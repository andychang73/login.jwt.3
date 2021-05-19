package com.example.abstractionizer.login.jwt3.login.services.impl;

import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;
import com.example.abstractionizer.login.jwt3.db.rmdb.mappers.UserMapper;
import com.example.abstractionizer.login.jwt3.enums.ErrorCode;
import com.example.abstractionizer.login.jwt3.exceptions.CustomException;
import com.example.abstractionizer.login.jwt3.login.services.UserService;
import com.example.abstractionizer.login.jwt3.models.bo.UserUpdateBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public User create(User user) {
        if(userMapper.insert(user) != 1){
            throw new CustomException(ErrorCode.DATA_INSERT_FAILED);
        }
        return user;
    }

    @Override
    public Optional<User> getUser(Integer userId, String username) {
        return Optional.ofNullable(userMapper.getByIdOrUsername(userId, username));
    }

    @Override
    public boolean isUserExists(String username) {
        return userMapper.countByUsername(username) > 0;
    }

    @Override
    public boolean isUserExists(Integer userId, String username) {
        return userMapper.countByUserIdOrUsername(userId, username) > 0;
    }

    @Override
    public void updateLastLoginTime(String username) {
        if(userMapper.updateLastLoginTime(username) != 1){
            throw new CustomException(ErrorCode.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public void updateUserInfo(Integer userId, UserUpdateBo bo) {
        if(userMapper.updateUserInfo(userId, bo) != 1){
            throw new CustomException(ErrorCode.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public void updatePassword(Integer userId, String password) {
        if(userMapper.updatePassword(userId, password) != 1){
            throw new CustomException(ErrorCode.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public UserInfoVo getUserInfo(Integer userId, String username) {
        return userMapper.selectByIdOrUsername(userId, username);
    }
}
