package com.example.gaia.controller;

import com.example.gaia.dto.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;

@RestControllerAdvice
public class ExceptionController {
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity threatDuplicateEntry(DataIntegrityViolationException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO("User alredy exists", "400");
    return ResponseEntity.badRequest().body(exceptionDTO);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity threat404(EntityNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity threatGeneralException(Exception exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
    return ResponseEntity.internalServerError().body(exceptionDTO);
  }

  @ExceptionHandler(NoSuchAlgorithmException.class)
  public ResponseEntity noSuchAlgorithmException(NoSuchAlgorithmException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO("No such algorithm was found on password hashing", "500");
    return ResponseEntity.internalServerError().body(exceptionDTO);
  }

}
