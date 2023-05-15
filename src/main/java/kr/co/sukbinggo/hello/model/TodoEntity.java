package kr.co.sukbinggo.hello.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoEntity {

  private String id;
  private String userid;
  private String title;
  private boolean done;
}
