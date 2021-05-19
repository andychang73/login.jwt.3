package com.example.abstractionizer.login.jwt3.login.businesses.impl;

import com.example.abstractionizer.login.jwt3.constants.RedisConstant;
import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;
import com.example.abstractionizer.login.jwt3.enums.ErrorCode;
import com.example.abstractionizer.login.jwt3.enums.UserStatus;
import com.example.abstractionizer.login.jwt3.exceptions.CustomException;
import com.example.abstractionizer.login.jwt3.login.businesses.UserBusiness;
import com.example.abstractionizer.login.jwt3.login.services.UserLoginService;
import com.example.abstractionizer.login.jwt3.login.services.UserRegistrationService;
import com.example.abstractionizer.login.jwt3.login.services.UserService;
import com.example.abstractionizer.login.jwt3.models.bo.ChangePasswordBo;
import com.example.abstractionizer.login.jwt3.models.bo.UserLoginBo;
import com.example.abstractionizer.login.jwt3.models.bo.UserRegisterBo;
import com.example.abstractionizer.login.jwt3.models.bo.UserUpdateBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import com.example.abstractionizer.login.jwt3.models.vo.UserLoginVo;
import com.example.abstractionizer.login.jwt3.utils.MD5Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserBusinessImpl implements UserBusiness {

    private final UserService userService;
    private final UserRegistrationService userRegistrationService;
    private final UserLoginService userLoginService;

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

    @Override
    public UserLoginVo login(UserLoginBo bo) throws JsonProcessingException {
        User user = userService.getUser(null, bo.getUsername()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIAL));

        userLoginService.authenticate(bo, user.getPassword());

        if(user.getStatus() != UserStatus.NORMAL.getStatus()){
            throw new CustomException(ErrorCode.ACCOUNT_FROZEN);
        }

        if(userLoginService.isUserCurrentlyLoggedIn(bo.getUsername())){
            throw new CustomException(ErrorCode.USER_LOGGED_IN);
        }


        UserInfoVo userInfoVo = new UserInfoVo().setUserId(user.getId()).setUsername(user.getUsername()).setEmail(user.getEmail()).setPhone(user.getPhone());

        final String jwt = userLoginService.generateToken(user.getUsername(), userInfoVo);
        userLoginService.setLoggedInUser(user.getUsername(), jwt);
        userService.updateLastLoginTime(user.getUsername());

        return new UserLoginVo().setToken(jwt);
    }

    @Override
    public UserInfoVo updateInfo(Integer userId, UserUpdateBo bo) {
        if(!userService.isUserExists(userId, null)){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        userService.updateUserInfo(userId, bo);

        return userService.getUserInfo(userId, null);
    }

    @Override
    public void changePassword(Integer userId, ChangePasswordBo bo) {
        User user = userService.getUser(userId, null).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!Objects.equals(MD5Util.md5(bo.getOldPassword()), user.getPassword())){
            throw new CustomException(ErrorCode.INVALID_CREDENTIAL);
        }
        if(Objects.equals(MD5Util.md5(bo.getNewPassword()), user.getPassword())){
            throw new CustomException(ErrorCode.NEW_OLD_PASSWORD_SAME);
        }
        if(!Objects.equals(bo.getNewPassword(), bo.getNewPassword2())){
            throw new CustomException(ErrorCode.NEW_PASSWORD_INCONSISTENCY);
        }
        userService.updatePassword(userId, bo.getNewPassword());
    }


}
