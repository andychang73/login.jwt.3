package com.example.abstractionizer.login.jwt3.login.businesses.impl;

import com.example.abstractionizer.login.jwt3.constants.RedisConstant;
import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;
import com.example.abstractionizer.login.jwt3.enums.ErrorCode;
import com.example.abstractionizer.login.jwt3.exceptions.CustomException;
import com.example.abstractionizer.login.jwt3.login.businesses.UserBusiness;
import com.example.abstractionizer.login.jwt3.login.services.UserRegistrationService;
import com.example.abstractionizer.login.jwt3.login.services.UserService;
import com.example.abstractionizer.login.jwt3.models.bo.UserRegisterBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import com.example.abstractionizer.login.jwt3.utils.MD5Util;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserBusinessImpl implements UserBusiness {

    private final UserService userService;
    private final UserRegistrationService userRegistrationService;

    @Override
    public void register(UserRegisterBo bo) {
        if(userService.isUserExists(bo.getUsername())){
            throw new CustomException(ErrorCode.USERNAME_EXISTS);
        }
        if(userRegistrationService.isUsernameRegistered(bo.getUsername())){
            throw new CustomException(ErrorCode.USERNAME_EXISTS);
        }

        User user = User.builder()
                .username(bo.getUsername())
                .password(MD5Util.md5(bo.getPassword()))
                .email(bo.getEmail())
                .phone(bo.getPhone())
                .build();

        String uuid = UUID.randomUUID().toString();
        userRegistrationService.setUserRegistrationInfo(uuid, user);
        userRegistrationService.setUserValidation(bo.getUsername());
        userRegistrationService.sendValidationToken(bo.getEmail(), "User Account Validation", uuid);
    }

    @Override
    public UserInfoVo validation(String uuid) {
        User user = userRegistrationService.getUserInfo(uuid).orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_VALIDATION_EXPIRED));

        userRegistrationService.deleteUserRegistrationInfo(RedisConstant.getUserRegistrationInfo(uuid));
        userRegistrationService.deleteUserValidationInfo(user.getUsername());
        userService.create(user);

        return new UserInfoVo().setUserId(user.getId()).setUsername(user.getUsername()).setEmail(user.getEmail()).setPhone(user.getPhone());
    }
}
