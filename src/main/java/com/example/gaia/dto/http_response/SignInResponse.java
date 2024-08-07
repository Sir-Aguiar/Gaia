package com.example.gaia.dto.http_response;

import com.example.gaia.dto.DefaultHTTP;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class SignInResponse extends DefaultHTTP {
  private String token;

  public SignInResponse(String message, HttpStatus status, String token) {
    super(message, status);
    this.token = token;
  }
}
