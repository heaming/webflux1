package com.example.webflux1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SampleController {

    @GetMapping("/sample/hello")
    public Mono<String> getHello() {

        // reactor
        // publisher <--> subscriber

        // [publisher]
        return Mono.just("hello rest controller with webflux");
    }
}
