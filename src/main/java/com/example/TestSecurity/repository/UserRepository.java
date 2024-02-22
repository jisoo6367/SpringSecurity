package com.example.TestSecurity.repository;

import com.example.TestSecurity.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> { //int는 오류남

    boolean existsByUsername(String username);
    // JPA 구문을 강제 커스텀해서 만들어야함
    // 존재하면 ture 아니면 false 리턴함

    UserEntity findByUsername(String username);


}
