package com.example.abstractionizer.login.jwt3.login.services;

import com.example.abstractionizer.login.jwt3.models.bo.UserLoginBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;

public interface UserLoginService {

    String generateToken(String username, UserInfoVo userInfoVo);

    void setLoggedInUser(String username, String token);

    boolean isUserCurrentlyLoggedIn(String username);

    void authenticate(UserLoginBo bo, String password);
}
