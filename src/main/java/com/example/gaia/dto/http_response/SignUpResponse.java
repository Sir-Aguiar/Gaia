package com.example.gaia.dto.http_response;

import com.example.gaia.dto.DefaultHTTP;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class SignUpResponse extends DefaultHTTP {
  private String token;

  public SignUpResponse(String message, HttpStatus status, String token) {
    super(message, status);
    this.token = token;
  }
}
