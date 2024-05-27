package com.ohgiraffers.jwtsecurity.auth.handler;

import com.ohgiraffers.jwtsecurity.auth.model.DetailsUser;
import com.ohgiraffers.jwtsecurity.common.AuthConstants;
import com.ohgiraffers.jwtsecurity.common.utils.ConvertUtil;
import com.ohgiraffers.jwtsecurity.common.utils.TokenUtils;
import com.ohgiraffers.jwtsecurity.user.dto.LoginUserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        LoginUserDTO user = ((DetailsUser) authentication.getPrincipal()).getUser();

        JSONObject jsonValue = (JSONObject) ConvertUtil.converObjectToJsonObject(user);
        HashMap<String, Object> responseMap = new HashMap<>();

        JSONObject jsonObject;

        String token = TokenUtils.generateJwtToken(user);
        responseMap.put("userInfo", jsonValue);
        responseMap.put("message", "로그인 성공입니다.");

        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);

        jsonObject = new JSONObject(responseMap);
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);
        printWriter.flush();
        printWriter.close();

    }
}
