package com.example.abstractionizer.login.jwt3.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
public class ApiException {
    private HttpStatus httpStatus;
    private List<String> errors;

    public ApiException(HttpStatus httpStatus, List<String> errors){
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public ApiException(HttpStatus httpStatus, String error){
        this.httpStatus = httpStatus;
        this.errors = Arrays.asList(error);
    }
}
