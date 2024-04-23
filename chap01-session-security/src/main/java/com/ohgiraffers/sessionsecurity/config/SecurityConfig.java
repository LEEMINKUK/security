package com.ohgiraffers.sessionsecurity.config;


import com.ohgiraffers.sessionsecurity.auth.model.AuthDetails;
import com.ohgiraffers.sessionsecurity.common.UserRole;
import com.ohgiraffers.sessionsecurity.config.handler.AuthFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthFailHandler authFailHandler;

    /* 비밀번호 암호화에 사용할 객체 BCryptPasswordEncoder bean 등록 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* 정적 리소스에 대한 요청은 제외하는 설정 */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        /* 요청에 대한 권한 체크 */
        http.authorizeHttpRequests( auth -> {
            // 로그인 하지 않아도 볼 수 있는 페이지 설정 (권한이 없어도 볼 수 있는 페이지)
            auth.requestMatchers("/auth/login", "/user/signup", "/auth/fail", "/", "/main").permitAll();

            auth.requestMatchers("/admin/*").hasAnyAuthority(UserRole.ADMIN.getRole());
            auth.requestMatchers("/user/*").hasAnyAuthority(UserRole.USER.getRole());
            auth.anyRequest().authenticated();

        }).formLogin( login -> {
            //로그인 페이지 찾아주는 기능
            login.loginPage("/auth/login");
            //login.html 과 값을 동일하게
            login.usernameParameter("user");
            login.passwordParameter("pass");
            //로그인 성공 하고 루트로 이동
            login.defaultSuccessUrl("/", true);
            //로그인 실패시
            login.failureHandler(authFailHandler);

        }).logout( logout -> {
            //로그아웃에 매핑 시키는 기능
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
            logout.deleteCookies("JSESSIONID");
            //세션 소멸 허용
            logout.invalidateHttpSession(true);
            //로그아웃 하고 루트로 이동
            logout.logoutSuccessUrl("/");

        }).sessionManagement( session -> {
            //세션의 갯수 1개로 제한
            session.maximumSessions(1);
            //세션의 만료후 루트로 이동
            session.invalidSessionUrl("/");

            //개발 할 동안 풀어 놓기위해 임시로 설정
        }).csrf( csrf -> csrf.disable());

        return http.build();
    }
}
