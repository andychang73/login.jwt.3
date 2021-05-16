package com.example.abstractionizer.login.jwt3.constants;

public class RedisConstant {

    public static final String USER_REGISTRATION_INFO = "LOGIN:USER:REGISTRATION:%s";
    public static final String USER_VALIDATION = "LOGIN:USER:VALIDATION:%s";
    public static final String LOGGED_IN_USER = "LOGIN:USER:LOGGED_IN:%s";
    public static final String LOGIN_FAILURE_COUNT = "LOGIN:USER:FAILURE:COUNT:%s";

    public static String getUserRegistrationInfo(String uuid){
        return String.format(USER_REGISTRATION_INFO, uuid);
    }

    public static String getUserValidation(String username){
        return String.format(USER_VALIDATION, username);
    }

    public static String getLoggedInUser(String username){
        return String.format(LOGGED_IN_USER, username);
    }

    public static String getLoginFailureCount(String username){
        return String.format(LOGIN_FAILURE_COUNT, username);
    }
}

