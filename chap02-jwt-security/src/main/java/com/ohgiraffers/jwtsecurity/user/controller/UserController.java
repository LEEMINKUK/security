package com.ohgiraffers.jwtsecurity.user.controller;

import com.ohgiraffers.jwtsecurity.user.dto.LoginUserDTO;
import com.ohgiraffers.jwtsecurity.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody LoginUserDTO user){
        return userService.signUp(user);
    }
}
