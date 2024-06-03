package com.example.webflux1.repository;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Test
    void save() {
        var user = User.builder().name("greg").email("greg@test.com").build();
        StepVerifier.create(userRepository.save(user))
                .assertNext(u -> {
                    assertEquals(1L, u.getId());
                    assertEquals("greg", u.getName());
                    assertEquals("greg@test.com", u.getEmail());
                })
                .verifyComplete();
    }

    @Test
    void findAll() {
        userRepository.save(User.builder().name("greg").email("greg@test.com").build());
        userRepository.save(User.builder().name("111").email("111@test.com").build());
        userRepository.save(User.builder().name("222").email("222@test.com").build());

        StepVerifier.create(userRepository.findAll())
                .expectNextCount(3)
                .verifyComplete();

    }

    @Test
    void findById() {
        userRepository.save(User.builder().name("greg").email("greg@test.com").build());
        userRepository.save(User.builder().name("222").email("222@test.com").build());

        StepVerifier.create(userRepository.findById(1L))
                .assertNext(u -> {
                    assertEquals(1L, u.getId());
                    assertEquals("greg", u.getName());
                    assertEquals("greg@test.com", u.getEmail());
                })
                .verifyComplete();
    }

    @Test
    void deleteById() {
        userRepository.save(User.builder().name("greg").email("greg@test.com").build());
        userRepository.save(User.builder().name("222").email("222@test.com").build());

        StepVerifier.create(userRepository.deleteById(2L))
                .expectNext(1)
                .verifyComplete();
    }
}