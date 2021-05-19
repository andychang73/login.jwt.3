package com.example.abstractionizer.login.jwt3.models.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ChangePasswordBo {

    @NotEmpty(message = "must not be null nor empty")
    private String oldPassword;

    @NotEmpty(message = "must not be null nor empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~!@#$%^&*()_=])(?=\\S+$).{8,}$")
    private String newPassword;

    @NotEmpty(message = "must not be null nor empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~!@#$%^&*()_=+])(?=\\S+$).{8,}$")
    private String newPassword2;
}
