package com.example.gaia.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class PasswordService {
  private final int algorithmMemory = 1024 * 10;
  private final Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(14, 32, 1, algorithmMemory, 10);

  public String getHashedPassword(String password) {
    return argon2PasswordEncoder.encode(password);
  }

  public boolean matchPassword(String password, String hashedPassword) {
    return this.argon2PasswordEncoder.matches(password, hashedPassword);
  }

}