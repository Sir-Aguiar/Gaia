package com.example.gaia.controller;

import com.example.gaia.dto.CreateUserDTO;
import com.example.gaia.entity.User;
import com.example.gaia.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController()
@RequestMapping("/user")
public class UserController {
  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<String> createUser(@RequestBody CreateUserDTO userDTO) throws NoSuchAlgorithmException {
    User newUser = new User(userDTO);
    userRepository.save(newUser);
    return new ResponseEntity<>("User created", HttpStatus.CREATED);
  }
}
