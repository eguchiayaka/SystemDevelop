package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @SuppressWarnings("deprecation")
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests -> authorizeRequests
                               .requestMatchers("/contact/**", "/admin/signup", "/admin/signin").permitAll()
                               .requestMatchers("/admin/**").authenticated()
                               .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                       .loginPage("/admin/signin")
                       .defaultSuccessUrl("/admin/top", true)
                       .permitAll()
                       .usernameParameter("email")
            )
            .logout(logout ->
	            logout
	                .logoutUrl("/admin/logout") // ログアウトURLを明示的に指定
	                .logoutSuccessUrl("/admin/signin") // ログアウト成功後のリダイレクト先
	                .permitAll()
	        );

        return http.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}