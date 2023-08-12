package com.example.service1.domains.users.errors;

import com.example.service1.lib.errors.BaseHttpException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class UsersErrors extends ResponseEntityExceptionHandler {

        public final UsersErrors USER_DEFAULT_ERROR = UsersError(new BaseHttpException(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Ocurrio un error inesperado.",
        "We have a unexpected error occurred.",
        "COMPANY_DEFAUL_ERROR"
    ));

    @ExceptionHandler(value = {Exception.class})
    private static UsersErrors UsersError(BaseHttpException data) {
        return null;
    }

    public UsersErrors getValues(){
        return this;
    }

}
