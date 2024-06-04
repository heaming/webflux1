package com.example.webflux1.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * @Title: PostCustomR2dbcRepository
 * @Usage: Post.java - User.java 연결을 위함
 */
public interface PostCustomR2dbcRepository {
    Flux<Post> findAllByUserId(Long userId);
}
