package com.example.abstractionizer.login.jwt3.models.vo;

import lombok.Data;

@Data
public class UserLoginVo {
    private String token;

    public UserLoginVo setToken(String token){
        this.token = token;
        return this;
    }
}
