package com.example.gaia.controller;

import com.example.gaia.dto.CreateUserDTO;
import com.example.gaia.dto.LoginDTO;
import com.example.gaia.dto.http_response.SignInResponse;
import com.example.gaia.dto.http_response.SignUpResponse;
import com.example.gaia.entity.User;
import com.example.gaia.errors.ApplicationError;
import com.example.gaia.repository.UserRepository;
import com.example.gaia.service.PasswordService;
import org.springframework.dao.DataIntegrityViolationException;
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
  public ResponseEntity createUser(@RequestBody CreateUserDTO userDTO) throws ApplicationError {
    try {
      User newUser = new User(userDTO);
      newUser.setPassword(passwordService.getHashedPassword(newUser.getPassword()));

      userRepository.save(newUser);

      SignUpResponse response = new SignUpResponse("Usuário criado com sucesso", HttpStatus.CREATED, "token");
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
      throw new ApplicationError("", 404, null);
    }

    SignInResponse response = new SignInResponse("Usuário logado com sucesso", HttpStatus.OK, "token");
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}
