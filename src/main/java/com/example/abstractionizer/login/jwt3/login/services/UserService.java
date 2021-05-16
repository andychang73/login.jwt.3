package com.example.abstractionizer.login.jwt3.login.services;

import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;

import java.util.Optional;

public interface UserService {

    User create(User user);

    Optional<User> getUser(String username);

    boolean isUserExists(String username);

    void updateLastLoginTime(String username);
}
