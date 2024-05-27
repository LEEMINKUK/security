package com.ohgiraffers.jwtsecurity.user.mapper;

import com.ohgiraffers.jwtsecurity.user.dto.LoginUserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    LoginUserDTO findByUsername(String userId);

    int regist(LoginUserDTO user);
}
