package com.ohgiraffers.jwtsecurity.auth.model.service;


import com.ohgiraffers.jwtsecurity.auth.model.DetailsUser;
import com.ohgiraffers.jwtsecurity.user.dto.LoginUserDTO;
import com.ohgiraffers.jwtsecurity.user.service.UserService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DetailsService implements UserDetailsService {

    private final UserService userService;

    public DetailsService(UserService userService) {
        this.userService = userService;
    }

    /* 로그인 요청 시 사용자의 id 를 받아 DB 에서 사용자 정보를 가져오는 메소드 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if (username == null || username.equals("")) {
//            throw new AuthenticationServiceException(username + "is Empty!!");
//        } else {
//            return userService.findUser(username)
//                    .map(data -> new DetailsUser(Optional.of(data)))
//                    .orElseThrows(() -> new AuthenticationServiceException(username));
//        }

        LoginUserDTO login = userService.findByUsername(username);

        if (Objects.isNull(login)){
            throw new UsernameNotFoundException("해당하는 회원 정보가 존재하지 않습니다.");
        }
        return new DetailsUser(login);
    }
}
