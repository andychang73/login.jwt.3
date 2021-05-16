package com.example.abstractionizer.login.jwt3.models.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginBo {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
