package com.example.gaia.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EnvironmentVariables {
  @Value("${jwt.secret}")
  private String jwtSecret;

}
