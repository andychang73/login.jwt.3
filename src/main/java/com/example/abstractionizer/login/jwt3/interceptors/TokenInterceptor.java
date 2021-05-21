package com.example.abstractionizer.login.jwt3.interceptors;

import com.example.abstractionizer.login.jwt3.enums.ErrorCode;
import com.example.abstractionizer.login.jwt3.exceptions.CustomException;
import com.example.abstractionizer.login.jwt3.login.services.UserLoginService;
import com.example.abstractionizer.login.jwt3.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@AllArgsConstructor
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final UserLoginService userLoginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String jwt = request.getHeader("Authorization").substring(7);
        log.info("jwt: {}", jwt);
        if(userLoginService.isTokenLoggedOut(jwt)){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        return !jwtUtil.isTokenExpired(jwt);
    }
}
