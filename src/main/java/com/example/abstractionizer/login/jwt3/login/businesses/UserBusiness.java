package com.example.abstractionizer.login.jwt3.login.businesses;

import com.example.abstractionizer.login.jwt3.models.bo.UserLoginBo;
import com.example.abstractionizer.login.jwt3.models.bo.UserRegisterBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import com.example.abstractionizer.login.jwt3.models.vo.UserLoginVo;

public interface UserBusiness {

    void register(UserRegisterBo bo);

    UserInfoVo validation(String validationToken);

    UserLoginVo login(UserLoginBo bo);
}
