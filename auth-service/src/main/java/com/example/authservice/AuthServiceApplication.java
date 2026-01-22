package com.example.authservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);

        log.info("GOOGLE_CLIENT_ID present={}, len={}",
                System.getenv("GOOGLE_CLIENT_ID") != null,
                System.getenv("GOOGLE_CLIENT_ID") == null ? 0 : System.getenv("GOOGLE_CLIENT_ID").length());

        log.info("GOOGLE_CLIENT_SECRET present={}, len={}",
                System.getenv("GOOGLE_CLIENT_SECRET") != null,
                System.getenv("GOOGLE_CLIENT_SECRET") == null ? 0 : System.getenv("GOOGLE_CLIENT_SECRET").length());
    }
}
