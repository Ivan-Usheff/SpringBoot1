package com.example.service1.domains.users.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.service1.lib.errors.BaseHttpException;


@RestControllerAdvice
public class UsersErrors extends RuntimeException {

    public static final BaseHttpException ERROR_DEFAULT_ERROR = new BaseHttpException(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Ocurrio un error inesperado.",
        "We have a unexpected error occurred.",
        "COMPANY_DEFAUL_ERROR"
    );
    
    public static final BaseHttpException ERROR_DONT_FOUND = new BaseHttpException(
        HttpStatus.BAD_REQUEST,
        "El usuario que busca no se encuentra.",
        "The user you are looking for cannot be found..",
        "ERROR_DONT_FOUND"
    );
    
    
    public static final BaseHttpException ERROR_ALREDY_EXISTS = new BaseHttpException(
        HttpStatus.BAD_REQUEST,
        "El usuario ya se encuentra en uso.",
        "The user already exist.",
        "ERROR_ALREDY_EXISTS"
    );

    public static final BaseHttpException ERROR_WITHOUT_ACTIVATE = new BaseHttpException(
        HttpStatus.UNAUTHORIZED,
        "El usuario no se encuentra activado.",
        "The user is not activated.",
        "ERROR_WITHOUT_ACTIVATE"
    );

    public static final BaseHttpException ERROR_ACOUNT_CLOSE = new BaseHttpException(
        HttpStatus.UNAUTHORIZED,
        "La cuenta se encuentra cerrada.",
        "The account is closed.",
        "ERROR_ACOUNT_CLOSE"
    );

    public static final BaseHttpException ERROR_BAD_REQUEST = new BaseHttpException(
        HttpStatus.BAD_REQUEST,
        "Los datos ingresados no son correctos.",
        "The data entered is not correct.",
        "ERROR_BAD_REQUEST"
    );
    
    public static final BaseHttpException ERROR_UNEXPECTED_ERROR = new BaseHttpException(
        HttpStatus.EXPECTATION_FAILED,
        "Ups! Ocurrio un error inesperado.",
        "Ups! An unexpected error occurred.",
        "ERROR_UNEXPECTED_ERROR"
    );


    @ExceptionHandler(Exception.class)
    private static UsersErrors UsersErrors(BaseHttpException data) {
        return null;
    }

}
