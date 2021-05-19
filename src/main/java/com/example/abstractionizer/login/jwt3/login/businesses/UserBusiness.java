package com.example.abstractionizer.login.jwt3.login.businesses;

import com.example.abstractionizer.login.jwt3.models.bo.ChangePasswordBo;
import com.example.abstractionizer.login.jwt3.models.bo.UserLoginBo;
import com.example.abstractionizer.login.jwt3.models.bo.UserRegisterBo;
import com.example.abstractionizer.login.jwt3.models.bo.UserUpdateBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import com.example.abstractionizer.login.jwt3.models.vo.UserLoginVo;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserBusiness {

    void register(UserRegisterBo bo);

    UserInfoVo validation(String validationToken);

    UserLoginVo login(UserLoginBo bo) throws JsonProcessingException;

    UserInfoVo updateInfo(Integer userId, UserUpdateBo bo);

    void changePassword(Integer userId, ChangePasswordBo bo);
}
