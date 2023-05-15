package kr.co.sukbinggo.hello.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReponseDTO<T> {
  private String error;
  private List<T> data;
}
