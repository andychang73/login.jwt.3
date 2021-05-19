package com.example.abstractionizer.login.jwt3.login.services;

import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;
import com.example.abstractionizer.login.jwt3.models.bo.UserUpdateBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;

import java.util.Optional;

public interface UserService {

    User create(User user);

    Optional<User> getUser(String username);

    boolean isUserExists(String username);

    boolean isUserExists(Integer userId, String username);

    void updateLastLoginTime(String username);

    void updateUserInfo(Integer userId, UserUpdateBo user);

    UserInfoVo getUser(Integer userId, String username);

}
