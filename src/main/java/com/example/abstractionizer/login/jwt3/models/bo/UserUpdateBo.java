package com.example.abstractionizer.login.jwt3.models.bo;

import com.example.abstractionizer.login.jwt3.annotations.NullOrNotBlank;
import lombok.Data;
import javax.validation.constraints.Pattern;

@Data
public class UserUpdateBo {

    @NullOrNotBlank(message = "Can be null but not empty")
    private String username;

    @NullOrNotBlank(message = "Can be null but not empty")
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email")
    private String email;

    @NullOrNotBlank(message = "Can be null but not empty")
    @Pattern(regexp = "^09\\d{8}", message = "Invalid number")
    private String phone;
}
