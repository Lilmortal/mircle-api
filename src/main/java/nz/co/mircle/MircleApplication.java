package nz.co.mircle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** This application is used to create the Mircle CRUD API endpoints. */
@SpringBootApplication
public class MircleApplication {
  public static void main(String[] args) {
    SpringApplication.run(MircleApplication.class, args);
  }
}
