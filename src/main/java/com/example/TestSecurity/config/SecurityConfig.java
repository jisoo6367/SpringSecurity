package com.example.TestSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

    http
            .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                    .anyRequest().authenticated()
            ); //상단에서부터 순서대로 적용되는 점 주의

    http
            .formLogin((auth) -> auth.loginPage("/login") //admin 페이지에서 인가되지않은 경우 오류 페이지 뜨지않게
                    .loginProcessingUrl("/loginProc")
                    .permitAll()
            );

    http
            .csrf((auth) -> auth.disable());


    return http.build();
    }

}
