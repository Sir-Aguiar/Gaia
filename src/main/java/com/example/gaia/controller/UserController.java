package com.example.gaia.controller;

import com.example.gaia.dto.CreateUserDTO;
import com.example.gaia.dto.LoginDTO;
import com.example.gaia.dto.http_response.SignInResponse;
import com.example.gaia.entity.User;
import com.example.gaia.errors.ApplicationError;
import com.example.gaia.repository.UserRepository;
import com.example.gaia.service.JWTService;
import com.example.gaia.service.PasswordService;
import com.example.gaia.service.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/user")
public class UserController {
  private final UserRepository userRepository;
  private final PasswordService passwordService;
  private final JWTService jwtService;

  @Autowired
  public UserController(UserRepository userRepository, JWTService jwtService) {
    this.userRepository = userRepository;
    this.passwordService = new PasswordService();
    this.jwtService = jwtService;
  }

  @PostMapping("/sign-up")
  public ResponseEntity createUser(@RequestBody CreateUserDTO userDTO) throws ApplicationError {
    try {
      User newUser = new User(userDTO);
      newUser.setPassword(passwordService.getHashedPassword(newUser.getPassword()));

      userRepository.save(newUser);

      SignInResponse response = new SignInResponse("Usuário criado com sucesso", HttpStatus.OK, JWTService.generateSessionToken(newUser));
      return ResponseEntity.status(response.getStatus()).body(response);
    } catch (DataIntegrityViolationException exception) {
      throw new ApplicationError("Nome de usuário já em uso", 400, null);
    }
  }

  @PostMapping("/sign-in")
  public ResponseEntity signIn(@RequestBody LoginDTO loginDTO) throws ApplicationError {
    User user = userRepository.findByUsername(loginDTO.getUsername());



    if (user == null) {
      throw new ApplicationError("", 404, null);
    }

    if (passwordService.matchPassword(loginDTO.getPassword(), user.getPassword())) {
      String token = JWTService.generateSessionToken(user);

      return ResponseHandler.getResponse("Usuário logado com sucesso", HttpStatus.OK, token);
    }

    throw new ApplicationError("Senha incorreta", 401, null);
  }
}
