package com.example.TestSecurity.dto;

import com.example.TestSecurity.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private  UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //사용자의 롤값을 리턴
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userEntity.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { //아이디 만료되었는지
        return true; //데이터베이스에 넣어놓지 않았기 때문에, 객체에서 가져오는게아니라 강제로 true 설정
    }

    @Override
    public boolean isAccountNonLocked() { //아이디 잠겨있는지
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
