package com.example.gaia.controller;

import com.example.gaia.errors.ApplicationError;
import com.example.gaia.dto.DefaultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(ApplicationError.class)
  public ResponseEntity<DefaultResponse> applicationError(ApplicationError exception) {
    DefaultResponse defaultResponse = new DefaultResponse(exception.cause, exception.code);
    return ResponseEntity.status(defaultResponse.status()).body(defaultResponse);
  }
}
