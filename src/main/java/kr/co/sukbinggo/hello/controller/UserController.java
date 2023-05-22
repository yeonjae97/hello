package kr.co.sukbinggo.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.sukbinggo.hello.dto.ResponseDTO;
import kr.co.sukbinggo.hello.dto.UserDTO;
import kr.co.sukbinggo.hello.model.UserEntity;
import kr.co.sukbinggo.hello.security.TokenProvider;
import kr.co.sukbinggo.hello.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private TokenProvider tokenProvider;

  @PostMapping("signup")
  public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
    try {
      if (userDTO == null || userDTO.getPassword() == null) {
        throw new RuntimeException("Invalid Password value.");
      }

      UserEntity user = UserEntity.builder()
          .username(userDTO.getUsername())
          .password(userDTO.getPassword())
          .build();

      UserEntity registeredUser = userService.create(user);
      UserDTO responseUserDTO = UserDTO.builder()
          .id(registeredUser.getId())
          .username(registeredUser.getUsername())
          .password(registeredUser.getPassword())
          .build();

      return ResponseEntity.ok().body(responseUserDTO);
    } catch (Exception e) {
      // String
      ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
      return ResponseEntity
          .badRequest()
          .body(responseDTO);
    }
  }

  @PostMapping("signin")
  public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
    UserEntity user = userService.getByCredentials(
        userDTO.getUsername(),
        userDTO.getPassword());

    if (user != null) {
      final String token = tokenProvider.create(user);
      log.info(token + "이 생성됨");

      final UserDTO responseUserDTO = UserDTO.builder()
          .username(user.getUsername())
          .id(user.getId())
          .token(token)
          .build();
      return ResponseEntity.ok().body(responseUserDTO);
    } else {
      ResponseDTO responseDTO = ResponseDTO.builder()
          .error("Login failed")
          .build();
      return ResponseEntity
          .badRequest()
          .body(responseDTO);
    }

  }
}
