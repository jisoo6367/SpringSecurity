package com.example.TestSecurity.service;

import com.example.TestSecurity.dto.JoinDTO;
import com.example.TestSecurity.entity.UserEntity;
import com.example.TestSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired //현재 필드 주입방식을 사용했으나 생성자 주입 방식으로 바꾸는 것이 좋다. 이유 찾아보기
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO){

        //DB에 이미 동일한 username을 가진 회원이 존재하는지?
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if (isUser) {
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }

}
