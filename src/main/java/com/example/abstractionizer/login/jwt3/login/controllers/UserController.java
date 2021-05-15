package com.example.abstractionizer.login.jwt3.login.controllers;

import com.example.abstractionizer.login.jwt3.login.businesses.UserBusiness;
import com.example.abstractionizer.login.jwt3.models.bo.UserRegisterBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import com.example.abstractionizer.login.jwt3.responses.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserBusiness userBusiness;

    @PostMapping
    public SuccessResponse register(@RequestBody @Valid UserRegisterBo bo){
        userBusiness.register(bo);
        return new SuccessResponse();
    }

    @GetMapping
    public SuccessResponse<UserInfoVo> validate(@RequestParam("token") String uuid){
        return new SuccessResponse<>(userBusiness.validation(uuid));
    }
}
