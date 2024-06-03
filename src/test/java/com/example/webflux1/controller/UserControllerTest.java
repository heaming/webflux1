package com.example.webflux1.controller;

import com.example.webflux1.dto.UserCreateRequest;
import com.example.webflux1.dto.UserResponse;
import com.example.webflux1.repository.User;
import com.example.webflux1.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest(UserController.class)
@AutoConfigureWebTestClient
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @Test
    void createUser() {
        when(userService.create("greg", "greg@test.com"))
                .thenReturn(
                        Mono.just(new User(1L, "greg", "greg@test.com", LocalDateTime.now(), LocalDateTime.now()))
                );

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequest("greg", "greg@test.com"))
                .exchange()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("greg", res.getName());
                    assertEquals("greg@test.com", res.getEmail());
                });
    }

    @Test
    void findAllUsers() {

        when(userService.findAll()).thenReturn(
                Flux.just(
                    new User(1L, "robin", "robin@test.com", LocalDateTime.now(), LocalDateTime.now()),
                    new User(2L, "jeremy", "jeremy@test.com", LocalDateTime.now(), LocalDateTime.now()),
                    new User(3L, "coco", "coco@test.com", LocalDateTime.now(), LocalDateTime.now())
                )
        );

        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(UserResponse.class)
                .hasSize(3);
    }

    @Test
    void findUserById() {
        when(userService.findById(1L))
            .thenReturn(
                    Mono.just(new User(1L, "greg", "greg@test.com", LocalDateTime.now(), LocalDateTime.now()))
            );

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("greg", res.getName());
                    assertEquals("greg@test.com", res.getEmail());
                });
    }

    @Test
    void notFoundUserById() {
        when(userService.findById(1L)).thenReturn(Mono.empty());

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void deleteUserById() {
        when(userService.deleteById(1L)).thenReturn(Mono.just(1));

        webTestClient.delete().uri("/users/1")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void updateUser() {
        when(userService.update(1L, "coco", "coco@update.com"))
                .thenReturn(
                        Mono.just(new User(1L, "coco", "coco@update.com", LocalDateTime.now(), LocalDateTime.now()))
                );

        webTestClient.put().uri("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequest("coco", "coco@update.com"))
                .exchange()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("coco", res.getName());
                    assertEquals("coco@update.com", res.getEmail());
                });

    }
}