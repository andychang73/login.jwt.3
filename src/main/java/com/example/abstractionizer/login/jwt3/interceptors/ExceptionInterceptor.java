package com.example.abstractionizer.login.jwt3.interceptors;

import com.example.abstractionizer.login.jwt3.exceptions.ApiException;
import com.example.abstractionizer.login.jwt3.exceptions.CustomException;
import com.example.abstractionizer.login.jwt3.models.vo.CustomExceptionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleAllExceptions(CustomException e){
        CustomExceptionVo response = new CustomExceptionVo(e.getCode(), e.getMsg(), e.getDetails());
        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus httpStatus, WebRequest request){
        List<String> errors = new ArrayList<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for(ObjectError error : ex.getBindingResult().getGlobalErrors()){
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return this.handleExceptionInternal(ex, new ApiException(HttpStatus.BAD_REQUEST, errors), headers, HttpStatus.BAD_REQUEST, request);
    }
}
