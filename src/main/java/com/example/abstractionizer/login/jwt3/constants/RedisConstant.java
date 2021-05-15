package com.example.abstractionizer.login.jwt3.constants;

public class RedisConstant {

    public static final String USER_REGISTRATION_INFO= "LOGIN:USER:REGISTRATION:%s";
    public static final String USER_VALIDATION = "LOGIN:USER:VALIDATION:%s";

    public static String getUserRegistrationInfo(String uuid){
        return String.format(USER_REGISTRATION_INFO, uuid);
    }

    public static String getUserValidation(String username){
        return String.format(USER_VALIDATION, username);
    }

}

