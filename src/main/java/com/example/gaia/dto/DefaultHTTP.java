package com.example.gaia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public abstract class DefaultHTTP {
  private String message;
  private HttpStatus status;

  public DefaultHTTP(String message, HttpStatus status) {
    this.message = message;
    this.status = status;
  }
}
