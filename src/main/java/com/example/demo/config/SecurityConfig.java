package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .headers(headers -> headers
            .addHeaderWriter((request, response) -> {
                response.setHeader("X-XSS-Protection", "1; mode=block"); // XSS-Protection 헤더 설정
            })
            )
            // .csrf(withDefaults())
            .sessionManagement(session -> session
                .invalidSessionUrl("/session-expired") // 세션 만료시 이동 페이지
                .maximumSessions(1) // 사용자 별 세션 최대 수
                .maxSessionsPreventsLogin(true) // 동시 세션 제한
            );
        return http.build(); // 필터 체인을 통해 보안설정(HttpSecurity)을 반환
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}