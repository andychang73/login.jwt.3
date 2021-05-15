package com.example.abstractionizer.login.jwt3.login.services;

import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;

import java.util.Optional;

public interface UserRegistrationService {

    void setUserRegistrationInfo(String username, User user);

    void setUserValidation(String username);

    Optional<User> getUserInfo(String username);

    void deleteUserRegistrationInfo(String username);

    void deleteUserValidationInfo(String username);

    boolean isUsernameRegistered(String username);

    void sendValidationToken(String to, String subject, String text);
}
