package com.ohgiraffers.jwtsecurity.user.service;

import com.ohgiraffers.jwtsecurity.common.UserRole;
import com.ohgiraffers.jwtsecurity.user.dto.LoginUserDTO;
import com.ohgiraffers.jwtsecurity.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public LoginUserDTO findByUsername(String username) {

        LoginUserDTO login = userMapper.findByUsername(username);

        if(!Objects.isNull(login)) {
            return login;
        } else {
            return null;
        }

    }

    public String signUp(LoginUserDTO user) {

        UserRole userRole = user.getUserRole();
        if(userRole == null || userRole.name().isEmpty()) {
            return "회원 가입 실패: userRole이 설정되지 않았습니다.";
        }

        user.setUserPass(bCryptPasswordEncoder.encode(user.getUserPass()));

        try {
            UserRole role = UserRole.valueOf(userRole.name().toUpperCase());
            user.setUserRole(role);
        } catch (IllegalArgumentException e) {
            return "회원 가입 실패 : 올바르지 않은 사용자 역할입니다.";
        }

        int result = userMapper.regist(user);

        if(result == 1) {
            return "회원 가입 성공!";
        } else {
            return "회원 가입 실패";
        }
    }
}
