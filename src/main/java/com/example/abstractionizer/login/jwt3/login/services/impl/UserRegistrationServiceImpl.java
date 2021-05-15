package com.example.abstractionizer.login.jwt3.login.services.impl;

import com.example.abstractionizer.login.jwt3.constants.RedisConstant;
import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;
import com.example.abstractionizer.login.jwt3.login.services.UserRegistrationService;
import com.example.abstractionizer.login.jwt3.utils.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final JavaMailSender mailSender;

    private final RedisUtil redisUtil;

    @Override
    public void setUserRegistrationInfo(String uuid, User user) {
        redisUtil.set(RedisConstant.getUserRegistrationInfo(uuid), user, 1L, TimeUnit.MINUTES);
    }

    @Override
    public void setUserValidation(String username) {
        redisUtil.set(RedisConstant.getUserValidation(username), username, 1L, TimeUnit.MINUTES);
    }

    @Override
    public Optional<User> getUserInfo(String uuid) {
        return Optional.ofNullable(redisUtil.get(RedisConstant.getUserRegistrationInfo(uuid), User.class));
    }

    @Override
    public void deleteUserRegistrationInfo(String username) {
        redisUtil.deleteKey(RedisConstant.getUserRegistrationInfo(username));
    }

    @Override
    public void deleteUserValidationInfo(String username) {
        redisUtil.deleteKey(RedisConstant.getUserValidation(username));
    }

    @Override
    public boolean isUsernameRegistered(String username) {
        return redisUtil.isKeyExists(RedisConstant.getUserValidation(username));
    }

    @Override
    public void sendValidationToken(String to, String subject, String uuid) {
        mailSender.send(simpleMailMessage(to, subject, uuid));
    }

    private SimpleMailMessage simpleMailMessage(String to, String subject, String uuid){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@abstractionizer.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(getValidationUrl(uuid));
        return message;
    }

    private String getValidationUrl(String uuid){
        return "http://127.0.0.1:8080/api/user?token=" + uuid;
    }
}
