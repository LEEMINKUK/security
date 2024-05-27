package com.ohgiraffers.jwtsecurity.auth.model;

import com.ohgiraffers.jwtsecurity.user.dto.LoginUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class DetailsUser implements UserDetails {

    private LoginUserDTO user;

    public DetailsUser() {
    }

    public DetailsUser(LoginUserDTO user) {
        this.user = user;
    }

    public LoginUserDTO getUser() {
        return user;
    }

    public void setUser(LoginUserDTO user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRole().forEach(role -> authorities.add(() -> role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUserPass();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
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