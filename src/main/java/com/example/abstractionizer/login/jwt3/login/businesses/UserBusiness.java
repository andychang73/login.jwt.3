package com.example.abstractionizer.login.jwt3.login.businesses;

import com.example.abstractionizer.login.jwt3.models.bo.UserRegisterBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;

public interface UserBusiness {

    void register(UserRegisterBo bo);

    UserInfoVo validation(String validationToken);
}
