package com.example.gaia.entity;

import com.example.gaia.dto.CreateUserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Permission permission;

  public User(CreateUserDTO userDTO) {
    this.username = userDTO.getUsername();
    this.name = userDTO.getName();
    this.password = userDTO.getPassword();
    this.permission = userDTO.getPermission();
  }
}
