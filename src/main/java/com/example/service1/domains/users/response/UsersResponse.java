package com.example.service1.domains.users.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.service1.domains.users.entities.UserEntity;

public class UsersResponse {
    

    public static ResponseEntity<UserEntity> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>(); // Noncompliant
            map.put("message", message);
            map.put("status", status.value());
            map.put("data", responseObj);

            return new ResponseEntity<UserEntity>(status); // Noncompliant
    }
}
