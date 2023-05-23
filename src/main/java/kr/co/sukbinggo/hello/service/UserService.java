package kr.co.sukbinggo.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.sukbinggo.hello.model.UserEntity;
import kr.co.sukbinggo.hello.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public UserEntity create(final UserEntity userEntity) {
    if (userEntity == null || userEntity.getUsername() == null) {
      throw new RuntimeException("invalid arguments");
    }

    final String username = userEntity.getUsername();

    if (userRepository.existsByUsername(username)) {
      String msg = "User name already exist";
      log.warn(msg + "{}", username);
      throw new RuntimeException(msg);
    }

    return userRepository.save(userEntity);
  }

  public UserEntity getByCredentials(final String username, final String password, PasswordEncoder encoder) { // 패스워드
                                                                                                              // 암호화
    UserEntity userEntity = userRepository.findByUsername(username);
    if (userEntity != null && encoder.matches(password, userEntity.getPassword())) {
      return userEntity;
    }
    return null;
    // return userRepository.findByUsernameAndPassword(username, password);
  }
}
