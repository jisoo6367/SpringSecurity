package com.example.TestSecurity.service;

import com.example.TestSecurity.dto.CustomUserDetails;
import com.example.TestSecurity.entity.UserEntity;
import com.example.TestSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired //실무에서는 여기서도 final 키워드사용하여 생성자주입 방식 쓰는 것이 좋다
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //데이터베이스에서 특정 username을 조회
        UserEntity userData = userRepository.findByUsername(username);

        if(userData != null){
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
