package com.example.gaia.controller;

import com.example.gaia.dto.CreateUserDTO;
import com.example.gaia.dto.LoginDTO;
import com.example.gaia.entity.User;
import com.example.gaia.repository.UserRepository;
import com.example.gaia.service.PasswordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UserController {
  private final UserRepository userRepository;
  private final PasswordService passwordService;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.passwordService = new PasswordService();
  }

  @PostMapping("/sign-up")
  public ResponseEntity<String> createUser(@RequestBody CreateUserDTO userDTO) {
    User newUser = new User(userDTO);
    newUser.setPassword(passwordService.getHashedPassword(newUser.getPassword()));

    userRepository.save(newUser);

    return new ResponseEntity<>("User created", HttpStatus.CREATED);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<String> signIn(@RequestBody LoginDTO loginDTO) {
    User user = userRepository.findByUsername(loginDTO.getUsername());

    if (user == null) {
      throw new EntityNotFoundException("Nenhum usuário foi encontrado com este login");
    }

    if (passwordService.matchPassword(loginDTO.getPassword(), user.getPassword())) {
      return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }

    return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
  }
}
