package com.ohgiraffers.jwtsecurity.user.dto;

import com.ohgiraffers.jwtsecurity.common.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginUserDTO {

    private int userCode;
    private String userId;
    private String userPass;
    private String userName;
    private UserRole userRole;

    public LoginUserDTO() {
    }

    public LoginUserDTO(int userCode, String userId, String userName, String userPass, UserRole userRole) {
        this.userCode = userCode;
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
        this.userRole = userRole;
    }

    public List<String> getRole() {
        if(this.userRole.getRole().length() > 0) {
            return Arrays.asList(this.userRole.getRole().split(","));
        }

        return new ArrayList<>();
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "LoginUserDTO{" +
                "userCode=" + userCode +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
