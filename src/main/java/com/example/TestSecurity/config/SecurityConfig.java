package com.example.TestSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


//    @Bean //계층 권한
//    public RoleHierarchy roleHierarchy() {
//
//        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//
//        hierarchy.setHierarchy("ROLE_C > ROLE_B\n" +
//                "ROLE_B > ROLE_A");
//
//        return hierarchy;
//    }


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
//            .formLogin((auth) -> auth.loginPage("/login") //admin 페이지에서 인가되지않은 경우 오류 페이지 뜨지않게
//                    .loginProcessingUrl("/loginProc")
//                    .permitAll()
//            );
        .httpBasic(Customizer.withDefaults()); //httpBasic 방식 : 브라우저 헤더에 alert창처럼 뜸

    //http
    //        .csrf((auth) -> auth.disable());
    //배포 환경에서는 csrf 공격 방지를 위해 csrf disable 설정을 제거하고 추가적인 설정을 진행해야 함


    http
            .sessionManagement((auth) -> auth
            .maximumSessions(1) //하나의 ID에서 최대 허용할 수 있는 동시 접속 중복 로그인 : 다중 로그인
            .maxSessionsPreventsLogin(true));
            //다중 로그인 값을 초과했을 경우 기존 걸 로그아웃시킬지(false), 새로 들어오는 로그인을 금지할지(true)


    http // 세션 고정 보호 (해킹으로부터 보호)
            .sessionManagement((auth) -> auth
                    .sessionFixation().changeSessionId());


    return http.build();
    }



//    @Bean //InMemory 방식 유저 저장
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User.builder()
//                .username("jisoo")
//                .password(bCryptPasswordEncoder().encode("jisoo"))
//                .roles("C")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("ciwon")
//                .password(bCryptPasswordEncoder().encode("ciwon"))
//                .roles("B")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }


}
