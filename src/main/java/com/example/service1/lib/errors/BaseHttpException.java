package com.example.service1.lib.errors;

import org.springframework.http.HttpStatus;

public class BaseHttpException  {
    public final HttpStatus status;
    public final String clientMessage;
    public final String serverMessage;
    public final String code;


    public BaseHttpException(HttpStatus status, String clientMessage, String serverMessage, String code) {
        this.status = status;
        this.clientMessage = clientMessage;
        this.serverMessage = serverMessage;
        this.code = code;
    }
}
