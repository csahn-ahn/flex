package me.univ.flex;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class FlexApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlexApplication.class, args);
  }

}
