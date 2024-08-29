package com.example.gaia.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
  public static ResponseEntity<Object> getResponse(String message, HttpStatus status, Object responseObject) {
    Map<String, Object> responseBody = new HashMap<>();

    responseBody.put("message", message);
    responseBody.put("status", status.value());
    responseBody.put("data", responseObject);

    return new ResponseEntity<>(responseBody, status);
  }
}
