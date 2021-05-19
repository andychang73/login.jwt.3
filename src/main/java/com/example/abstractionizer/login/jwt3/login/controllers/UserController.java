package com.example.abstractionizer.login.jwt3.login.controllers;

import com.example.abstractionizer.login.jwt3.login.businesses.UserBusiness;
import com.example.abstractionizer.login.jwt3.models.bo.ChangePasswordBo;
import com.example.abstractionizer.login.jwt3.models.bo.UserLoginBo;
import com.example.abstractionizer.login.jwt3.models.bo.UserRegisterBo;
import com.example.abstractionizer.login.jwt3.models.bo.UserUpdateBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import com.example.abstractionizer.login.jwt3.models.vo.UserLoginVo;
import com.example.abstractionizer.login.jwt3.responses.SuccessResponse;
import com.example.abstractionizer.login.jwt3.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController{

    private final UserBusiness userBusiness;

    public UserController(JwtUtil jwtUtil, UserBusiness userBusiness) {
        super(jwtUtil);
        this.userBusiness = userBusiness;
    }

    @PostMapping
    public SuccessResponse register(@RequestBody @Valid UserRegisterBo bo){
        userBusiness.register(bo);
        return new SuccessResponse();
    }

    @GetMapping
    public SuccessResponse<UserInfoVo> validate(@RequestParam("token") String uuid){
        return new SuccessResponse<>(userBusiness.validation(uuid));
    }

    @PutMapping("/update")
    public SuccessResponse<UserInfoVo> update(@RequestHeader("Authorization") String token,@RequestBody @Valid UserUpdateBo bo) throws JsonProcessingException {
        return new SuccessResponse<>(userBusiness.updateInfo(this.getUserInfo(token).getUserId(),bo));
    }

    @PostMapping("/login")
    public SuccessResponse<UserLoginVo> login(@RequestBody @Valid UserLoginBo bo) throws JsonProcessingException {
        return new SuccessResponse<>(userBusiness.login(bo));
    }

    @PutMapping("/changePassword")
    public SuccessResponse changePassword(@RequestHeader("Authorization") String token, @RequestBody @Valid ChangePasswordBo bo) throws JsonProcessingException {
        userBusiness.changePassword(this.getUserInfo(token).getUserId(), bo);
        return new SuccessResponse();
    }
}
