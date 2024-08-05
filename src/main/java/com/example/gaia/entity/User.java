package com.example.gaia.entity;

import com.example.gaia.dto.CreateUserDTO;
import com.example.gaia.service.PasswordService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.NoSuchAlgorithmException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  private String username;

  private String name;

  private String password;

  @Enumerated(EnumType.STRING)
  private Permission permission;

  public User(CreateUserDTO userDTO) throws NoSuchAlgorithmException {
    PasswordService passwordService = new PasswordService();
    this.username = userDTO.getUsername();
    this.name = userDTO.getName();
    this.password = passwordService.getHashedPassword(userDTO.getPassword());
    this.permission = userDTO.getPermission();
  }
}
