package com.example.abstractionizer.login.jwt3.login.services.impl;

import com.example.abstractionizer.login.jwt3.constants.RedisConstant;
import com.example.abstractionizer.login.jwt3.enums.ErrorCode;
import com.example.abstractionizer.login.jwt3.exceptions.CustomException;
import com.example.abstractionizer.login.jwt3.login.services.UserLoginService;
import com.example.abstractionizer.login.jwt3.models.bo.UserLoginBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import com.example.abstractionizer.login.jwt3.utils.JwtUtil;
import com.example.abstractionizer.login.jwt3.utils.MD5Util;
import com.example.abstractionizer.login.jwt3.utils.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
@Service
public class UserLoginServiceImpl implements UserLoginService {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public String generateToken(@NonNull final String username, @NonNull final UserInfoVo userInfoVo) {
        return jwtUtil.generateToken(username, userInfoVo);
    }

    @Override
    public void setLoggedInUser(@NonNull final String username, @NonNull final String token) {
        redisUtil.set(RedisConstant.getLoggedInUser(username), token, 1L, TimeUnit.MINUTES);
    }

    @Override
    public boolean isUserCurrentlyLoggedIn(String username) {
        return redisUtil.isKeyExists(RedisConstant.getLoggedInUser(username));
    }

    @Override
    public void authenticate(UserLoginBo bo, String password) {
        if (!Objects.equals(MD5Util.md5(bo.getPassword()), password)) {
            if (loginFailureCount(RedisConstant.getLoginFailureCount(bo.getUsername())) >= 3) {
                throw new CustomException(ErrorCode.ACCOUNT_FROZEN);
            }
            throw new CustomException(ErrorCode.INVALID_CREDENTIAL);
        }
    }

    @Override
    public void logOut(String token, Long duration) {
        redisUtil.set(RedisConstant.getUserLogOut(token), token, duration, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isTokenLoggedOut(String token) {
        return redisUtil.isKeyExists(RedisConstant.getUserLogOut(token));
    }

    public Long loginFailureCount(String key) {
        Long count = 1L;
        if(redisUtil.isKeyExists(key)){
            return redisUtil.increment(key, count);
        }
        redisUtil.set(key, count, 3L, TimeUnit.MINUTES);
        return count;
    }

    public static void main(String[] args){
        System.out.println(MD5Util.md5("Abc123456!"));
    }
}
