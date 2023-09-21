package com.example.service1.lib.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.service1.lib.errors.BaseHttpException;

public class CustomResponse {

    private CustomResponse() {
        throw new IllegalStateException("Utility class");
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateErrorResponse(BaseHttpException exception){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", exception.clientMessage);
        map.put("status", exception.status.value());
        map.put("serverMessage", exception.serverMessage);
        map.put("code", exception.code);

        return new ResponseEntity<Object>(map, exception.status);
    }
    

    public static ResponseEntity<Object> generateErrorResponse(BaseHttpException exception, Object extra){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", exception.clientMessage);
        map.put("status", exception.status.value());
        map.put("serverMessage", exception.serverMessage);
        map.put("code", exception.code);
        map.put("response", extra);

        return new ResponseEntity<Object>(map, exception.status);
    }
}
