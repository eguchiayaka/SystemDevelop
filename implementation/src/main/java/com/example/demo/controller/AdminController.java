package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Admin;
import com.example.demo.form.LoginEditForm;
import com.example.demo.form.LoginForm;
import com.example.demo.form.SignupForm;
import com.example.demo.form.UserEditForm;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.AdminServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private AdminServiceImpl adminServiceImpl;
    
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "signup"; // テンプレートのパス
    }

    @PostMapping("/signup")
    public String registerUser(@Validated @ModelAttribute("signupForm") SignupForm signupForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        } else {
            adminServiceImpl.saveAdmin(signupForm);
            return "redirect:/admin/top";
        }
    }

    @GetMapping("/signin")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }
    
    @GetMapping("/top")
    public String showTopPage(Model model) {
        return "top";
    }
    
    @PostMapping("/top")
    public String myPage(Model model) {
        // 現在のログインユーザ情報を取得
        Admin loggedInUser = adminServiceImpl.getLoggedInUser();
        // ログインユーザーのIDを使用してリダイレクト
        return "redirect:/admin/mypage/" + loggedInUser.getId();
    }

    @GetMapping("/mypage/{id}")
    public String showProfile(@PathVariable("id") Long id, Model model) {
        Admin admin = adminServiceImpl.findById(id);
        model.addAttribute("admin", admin);
        return "login_details"; // プロフィールページのテンプレート名
    }

    @PostMapping("/mypage/{id}")
    public String loginEdit(@PathVariable Long id, Model model) {
        return "redirect:/admin/mypage/" + id + "/edit"; // 編集が完了したら編集ページにリダイレクト
    }

    @GetMapping("/mypage/{id}/edit")
    public String showLoginEditForm(@PathVariable("id") Long id, Model model) {
        Admin admin = adminServiceImpl.findById(id);
        LoginEditForm loginEditForm = new LoginEditForm();

        // LoginEditFormにユーザー情報を設定
        loginEditForm.setId(admin.getId());
        loginEditForm.setStore_id(admin.getStore_id());
        loginEditForm.setFirst_name(admin.getFirst_name());
        loginEditForm.setLast_name(admin.getLast_name());
        loginEditForm.setEmail(admin.getEmail());
        loginEditForm.setPosition_id(admin.getPosition_id());
        loginEditForm.setAuthority_id(admin.getAuthority_id());
        loginEditForm.setPhone(admin.getPhone());

        model.addAttribute("admin", admin);
        model.addAttribute("loginEditForm", loginEditForm);
        return "login_edit";
    }

    @PostMapping("/mypage/{id}/edit")
    public String processLoginEditForm(@PathVariable Long id,
                                       @ModelAttribute @Valid LoginEditForm loginEditForm,
                                       BindingResult bindingResult,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.toString()));
            return "login_edit"; // バリデーションエラーがある場合は編集ページを再表示
        }

        adminServiceImpl.updateAdmin(id, loginEditForm); // ユーザーを更新
        // 更新後にリダイレクトするURLを指定
        return "redirect:/admin/mypage/" + id; // 編集が完了したらリダイレ
    }

    @GetMapping("/users")
    public String listAdmins(Model model) {
        List<Admin> admins = adminRepository.findAll();
        model.addAttribute("admins", admins);
        return "users"; // users.htmlを返す
    }
    
    @GetMapping("/users/{id}")
    public String showAdminDetail(@PathVariable("id") Long id, Model model) {
        Admin admin = adminServiceImpl.findById(id);
        model.addAttribute("admin", admin);
        return "users_detail";
    }
    
    @PostMapping("/users/{id}")
    public String edit(@PathVariable Long id,
                       @RequestParam("action") String action,
                       Model model) {
        if ("delete".equals(action)) {
            // 削除ボタンが押された場合の処理
            adminServiceImpl.deleteAdmin(id);
            return "redirect:/admin/users"; // 削除が完了したら一覧ページにリダイレクト
        } else if ("edit".equals(action)) {
        
            return "redirect:/admin/users/" + id + "/edit"; // 編集が完了したら編集ページにリダイレクト
        }
        return "redirect:/admin/users"; // 何もせずに一覧ページにリダイレクト（本来はありえないが、安全策）
    }


    @GetMapping("/users/{id}/edit")
    public String showUserEditForm(@PathVariable("id") Long id, Model model) {
        Admin admin = adminServiceImpl.findById(id);
        UserEditForm userEditForm = new UserEditForm();
        
        userEditForm.setId(admin.getId());
        userEditForm.setStore_id(admin.getStore_id());
        userEditForm.setFirst_name(admin.getFirst_name());
        userEditForm.setLast_name(admin.getLast_name());
        userEditForm.setEmail(admin.getEmail());
        userEditForm.setPosition_id(admin.getPosition_id());
        userEditForm.setAuthority_id(admin.getAuthority_id());
        userEditForm.setPhone(admin.getPhone());
        
        model.addAttribute("admin", admin);
        model.addAttribute("userEditForm", userEditForm);
        return "users_edit";
    }
    
    @PostMapping("/users/{id}/edit")
    public String processEditForm(@PathVariable Long id,
                                  @ModelAttribute @Valid UserEditForm userEditForm,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.toString()));
            return "users_edit"; // バリデーションエラーがある場合は編集ページを再表示
        }

        adminServiceImpl.updateUser(id, userEditForm); // ユーザーを更新

        // 更新後にリダイレクトするURLを指定
        return "redirect:/admin/users/" + id; // 編集が完了したらリダイレクト
    }


    }


