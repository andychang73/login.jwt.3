package com.example.abstractionizer.login.jwt3.login.controllers;

import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import com.example.abstractionizer.login.jwt3.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

public class BaseController {

    private final JwtUtil jwtUtil;

    public BaseController(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    protected UserInfoVo getUserInfo(String token) throws JsonProcessingException {
        return jwtUtil.getUserInfoFromToken(token.substring(7));
    }
}
