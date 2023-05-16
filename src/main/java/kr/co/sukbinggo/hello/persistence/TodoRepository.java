package kr.co.sukbinggo.hello.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.sukbinggo.hello.model.TodoEntity;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

  // 정의하고 싶은 쿼리문이 있으면 더 작성하면 된다.
  // 만약에 커스텀 쿼리를 하고 싶으면 query어노테이션을 쓰면 된다.

  List<TodoEntity> findByUserid(String userid);

}