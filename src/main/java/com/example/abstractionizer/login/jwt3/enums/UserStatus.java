package com.example.abstractionizer.login.jwt3.enums;

public enum UserStatus {

    FROZEN(0),
    NORMAL(1);

    private int status;

    UserStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }
}
