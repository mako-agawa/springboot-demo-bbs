package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/auth/register", "/auth/login").permitAll()
                .anyRequest().authenticated()

        ).formLogin(form -> form.loginPage("/auth/login") // 画面
                .loginProcessingUrl("/auth/login") // ★フォームPOST先と一致（小文字）
                .defaultSuccessUrl("/posts", true) // 成功時は必ず /posts
                .permitAll()

        ).logout(logout -> logout.logoutUrl("/auth/logout").logoutSuccessUrl("/auth/login?logout")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .permitAll()

        );
        return http.build();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
