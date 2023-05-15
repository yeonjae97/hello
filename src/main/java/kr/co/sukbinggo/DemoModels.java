package kr.co.sukbinggo;

import javax.annotation.Nonnull;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class DemoModels {
  @Nonnull
  private String id;

  public static void main(String[] args) {
    DemoModels demoModels = DemoModels.builder()
        .id("abcd")
        .build();
    System.out.println(demoModels);
  }
}
