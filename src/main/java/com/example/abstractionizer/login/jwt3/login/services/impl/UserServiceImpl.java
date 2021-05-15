package com.example.abstractionizer.login.jwt3.login.services.impl;

import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;
import com.example.abstractionizer.login.jwt3.db.rmdb.mappers.UserMapper;
import com.example.abstractionizer.login.jwt3.enums.ErrorCode;
import com.example.abstractionizer.login.jwt3.exceptions.CustomException;
import com.example.abstractionizer.login.jwt3.login.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public boolean isUserExists(String username) {
        return userMapper.countByUsername(username) > 0;
    }
}
