package kr.co.sukbinggo.hello.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.sukbinggo.hello.dto.ResponseDTO;
import kr.co.sukbinggo.hello.dto.TestRequestBodyDTO;

@RestController
@RequestMapping("test") // 리소스
public class TestController {
  @GetMapping
  public String testController() {
    return "hello world";
  }

  @GetMapping("/testGetMapping")
  public String testControllerWithPath() {
    return "Hello World! testGetMapping";
  }

  @GetMapping("{id}")
  public String testControllerWithPathVariable(@PathVariable(required = false) int id) {
    return "Hello World! ID " + id;
  }

  @GetMapping("requestParam")
  public String testControllerRequestParam(@RequestParam(defaultValue = "20") Integer id) {
    return "Hello World! ID " + id;
  }

  @GetMapping("requestBody")
  public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
    return "Hello World! ID " + testRequestBodyDTO.getId() + " Message : " + testRequestBodyDTO.getMessage();
  }

  @GetMapping("requestDTO")
  public String testControllerDTO(TestRequestBodyDTO testRequestBodyDTO) {
    return "Hello World! ID " + testRequestBodyDTO.getId() + " Message : " + testRequestBodyDTO.getMessage();
  }

  @GetMapping("responseBody")
  public ResponseDTO<String> testControllerResponseBody() {
    List<String> list = new ArrayList<>();
    list.add("Hello World! I'm ResponseDTO");
    ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
    return responseDTO;
  }

  @GetMapping("testResponseEntity")
  public ResponseEntity<?> testControllerResponseEntity() {
    List<String> list = new ArrayList<>();
    list.add("Hello World! I'm ResponseEntity. And you got 400!");
    ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
    return ResponseEntity.badRequest().body(responseDTO);
  }
}
