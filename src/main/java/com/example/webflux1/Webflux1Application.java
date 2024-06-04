package com.example.webflux1;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication
public class Webflux1Application {

    public static void main(String[] args) {
        SpringApplication.run(Webflux1Application.class, args);
    }

}
