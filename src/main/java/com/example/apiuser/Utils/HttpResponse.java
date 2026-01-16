package com.example.apiuser.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {


    public static ResponseEntity<Object> send(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", java.time.LocalDateTime.now());
        map.put("status", status.value());
        map.put("message", message);
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map, status);
    }


    public static ResponseEntity<Object> send(String message, HttpStatus status) {
        return send(message, status, null);
    }
}
