package com.example.abstractionizer.login.jwt3.models.bo;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterBo {

    @NotEmpty(message = "must not be null nor empty")
    private String username;

    @NotEmpty(message = "must not be null nor empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Invalid password")
    private String password;

    @NotEmpty(message = "must not be null nor empty")
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email")
    private String email;

    @NotEmpty(message = "must not be null nor empty")
    @Pattern(regexp = "09\\d{8}", message = "Invalid phone number")
    private String phone;
}
