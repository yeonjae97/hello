package kr.co.sukbinggo.hello.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.sukbinggo.hello.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

  UserEntity findByUsername(String username);

  boolean existsByUsername(String username);

  UserEntity findByUsernameAndPassword(String username, String password);

}
