package com.example.abstractionizer.login.jwt3.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements BaseError{
    DATA_INSERT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "10000", "Data insert failed"),
    DATA_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "10001", "Data update failed"),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "10002", "Data not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "10003", "User account does not exist"),
    USER_LOGGED_IN(HttpStatus.INTERNAL_SERVER_ERROR, "10004", "This account has already been logged in"),
    USERNAME_EXISTS(HttpStatus.INTERNAL_SERVER_ERROR, "10005", "Username already exists"),
    INVALID_CREDENTIAL(HttpStatus.INTERNAL_SERVER_ERROR, "10006", "Invalid Credential"),
    ACCOUNT_FROZEN(HttpStatus.INTERNAL_SERVER_ERROR, "10007", "Your account has been frozen, please contact admin"),
    ACCOUNT_VALIDATION_EXPIRED(HttpStatus.NOT_FOUND, "10008", "Account validation is expired"),
    ACCOUNT_NOT_VALIDATED(HttpStatus.INTERNAL_SERVER_ERROR, "10009", "Account has not yet been validated"),
    NO_INFO_TO_UPDATE(HttpStatus.BAD_REQUEST, "10010", "No update info"),
    NEW_OLD_PASSWORD_SAME(HttpStatus.BAD_REQUEST, "10011", "New password and old password cannot be the same"),
    NEW_PASSWORD_INCONSISTENCY(HttpStatus.BAD_REQUEST, "10012", "New passwords must be the same"),
    INVALID_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "10013", "Invalid Token")
    ;

    HttpStatus httpStatus;
    String code;
    String msg;

    ErrorCode(HttpStatus httpStatus, String code, String msg){
        this.httpStatus = httpStatus;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
