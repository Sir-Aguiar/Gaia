package com.example.gaia.dto;

import com.example.gaia.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
  private String username;
  private String name;
  private String password;
  private Permission permission;
}
