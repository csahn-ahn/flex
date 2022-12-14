package me.univ.flex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FlexApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlexApplication.class, args);
  }

}
