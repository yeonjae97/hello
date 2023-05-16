package kr.co.sukbinggo.hello.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sukbinggo.hello.model.TodoEntity;
import kr.co.sukbinggo.hello.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TodoService {

  @Autowired
  private TodoRepository repository;

  public String testService() {
    TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
    repository.save(entity);
    TodoEntity savedEntity = repository.findById(entity.getId()).get();

    return savedEntity.getTitle(); // 등록되어 있는 글의 제목을 반환
  }

  public List<TodoEntity> create(final TodoEntity entity) {
    // validations
    validate(entity);
    repository.save(entity);

    log.info("Entity id : {} is saved.", entity.getId());

    return repository.findByUserid(entity.getUserid());
  }

  // 리펙토링한 메서드
  public void validate(final TodoEntity entity) {
    // validations
    if (entity == null) {
      log.warn("Entity cannot be null");
      throw new RuntimeException("Entity cannot be null.");
    }
    if (entity.getUserid() == null) {
      log.warn("Unknown user.");
      throw new RuntimeException("Unknown user.");
    }
  }

  public List<TodoEntity> retrieve(final String userId) {
    return repository.findByUserid(userId);
  }

  public List<TodoEntity> update(final TodoEntity entity) {
    validate(entity);

    final Optional<TodoEntity> original = repository.findById(entity.getId());

    original.ifPresent(todo -> {
      todo.setTitle(entity.getTitle());
      todo.setDone(entity.isDone());

      repository.save(todo);
    });
    return retrieve(entity.getUserid());
  }

  public List<TodoEntity> delete(final TodoEntity entity) {
    validate(entity);

    try {
      repository.delete(entity);
    } catch (Exception e) {
      log.error("error deleting entity ", entity.getId(), e);
      throw new RuntimeException("error deleting entity " + entity.getId());
    }
    return retrieve(entity.getUserid());
  }

}
