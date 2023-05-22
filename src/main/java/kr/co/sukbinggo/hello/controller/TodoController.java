package kr.co.sukbinggo.hello.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.sukbinggo.hello.dto.ResponseDTO;
import kr.co.sukbinggo.hello.dto.TodoDTO;
import kr.co.sukbinggo.hello.model.TodoEntity;
import kr.co.sukbinggo.hello.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {

  @Autowired
  private TodoService service;

  // testTodo 메서드 작성
  @GetMapping("test")
  public ResponseEntity<?> testTodo() {
    List<String> list = new ArrayList<>();
    list.add(service.testService());
    list.add(service.testService());
    list.add(service.testService());
    list.add(service.testService());
    ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
    try {
      String temporaryUserId = "temporary-user";
      TodoEntity entity = TodoDTO.toEntity(dto);
      entity.setUserid(temporaryUserId);
      List<TodoEntity> entities = service.create(entity);

      List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      String error = e.getMessage();
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(response);
    }

  }

  @GetMapping
  public ResponseEntity<?> retrieveTodoList() {

    String temporaryUserId = "temporary-user";

    List<TodoEntity> entities = service.retrieve(temporaryUserId);

    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

    return ResponseEntity.ok().body(response);

  }

  @PutMapping
  public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
    String temporaryUserId = "temporary-user";

    TodoEntity entity = TodoDTO.toEntity(dto);

    entity.setUserid(temporaryUserId);

    List<TodoEntity> entities = service.update(entity);

    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

    return ResponseEntity.ok().body(response);

  }

  @DeleteMapping
  public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
    try {

      String temporaryUserId = "temporary-user";

      TodoEntity entity = TodoDTO.toEntity(dto);

      entity.setUserid(temporaryUserId);

      List<TodoEntity> entities = service.delete(entity);

      List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
      return ResponseEntity.ok().body(response);

    } catch (Exception e) {
      String error = e.getMessage();
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(response);
    }

  }
}
