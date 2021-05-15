package com.example.abstractionizer.login.jwt3.login.services;

import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;

public interface UserService {

    User create(User user);

    boolean isUserExists(String username);
}
