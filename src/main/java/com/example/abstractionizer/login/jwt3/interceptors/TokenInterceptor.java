package com.example.abstractionizer.login.jwt3.interceptors;

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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String jwt = request.getHeader("Authorization").substring(7);
        return !jwtUtil.isTokenExpired(jwt);
    }
}
