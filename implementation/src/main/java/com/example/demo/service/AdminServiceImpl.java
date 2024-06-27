package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.form.LoginEditForm;
import com.example.demo.form.SignupForm;
import com.example.demo.form.UserEditForm;
import com.example.demo.repository.AdminRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminServiceImpl implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public Admin saveAdmin(SignupForm signupForm) {
        // TODO 自動生成されたメソッド・スタブ
        Admin admin = new Admin();
        admin.setStore_id(signupForm.getStore_id());
        admin.setFirst_name(signupForm.getFirst_name());
        admin.setLast_name(signupForm.getLast_name());
        admin.setEmail(signupForm.getEmail());
        admin.setPosition_id(signupForm.getPosition_id());
        admin.setAuthority_id(signupForm.getAuthority_id());
        admin.setPhone(signupForm.getPhone());
        admin.setPassword(passwordEncoder.encode(signupForm.getPassword()));

        return adminRepository.save(admin);
    }
 
    // 現在ログインしているユーザーを取得するメソッド
    public Admin getLoggedInUser() {
        // SecurityContextHolder から認証情報を取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 認証情報が存在しない場合や、認証が無効な場合は null を返す
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        // 認証情報が UserDetails から取得可能か確認
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            // ユーザーリポジトリからメールアドレスでユーザーを検索して返す
            return adminRepository.findByEmail(email);
        }

        // UserDetails ではない場合も null を返す
        return null;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                admin.getEmail(),
                admin.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
    
    public void updateAdmin(Long userId, LoginEditForm loginEditForm) {
    	 Admin admin = adminRepository.findById(userId).orElse(new Admin());
    	    // LoginEditFormからのデータをセット
		admin.setId(userId);
		admin.setStore_id(loginEditForm.getStore_id());
		admin.setLast_name(loginEditForm.getLast_name());
		admin.setFirst_name(loginEditForm.getFirst_name());
		admin.setEmail(loginEditForm.getEmail());
		admin.setPosition_id(loginEditForm.getPosition_id());
		admin.setAuthority_id(loginEditForm.getAuthority_id());
		admin.setPhone(loginEditForm.getPhone());
		 admin.setPassword(passwordEncoder.encode(loginEditForm.getPassword()));
		admin.setUpdatedAt(LocalDateTime.now());

		adminRepository.save(admin);
	}
    
    
    
    public void updateUser(Long userId, UserEditForm userEditForm) {
   	 Admin admin = adminRepository.findById(userId).orElse(new Admin());
   	    // LoginEditFormからのデータをセット
		admin.setId(userId);
		admin.setStore_id(userEditForm.getStore_id());
		admin.setLast_name(userEditForm.getLast_name());
		admin.setFirst_name(userEditForm.getFirst_name());
		admin.setEmail(userEditForm.getEmail());
		admin.setPosition_id(userEditForm.getPosition_id());
		admin.setAuthority_id(userEditForm.getAuthority_id());
		admin.setPhone(userEditForm.getPhone());
		admin.setUpdatedAt(LocalDateTime.now());
		
		adminRepository.save(admin);
	}
   
    

	public Admin findById(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		return adminRepository.findById(id).orElse(null);
	
	}
	public void deleteAdmin(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		adminRepository.deleteById(id);
	}
}