package com.example.webflux1.controller;

import com.example.webflux1.dto.PostCreateRequest;
import com.example.webflux1.dto.PostResponse;
import com.example.webflux1.dto.PostResponseV2;
import com.example.webflux1.service.PostsServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/posts")
public class PostControllerV2 {
    private final PostsServiceV2 postsServiceV2;

    @PostMapping("")
    public Mono<PostResponseV2> createPost(@RequestBody PostCreateRequest request) {
        return postsServiceV2.create(request.getUserId(), request.getTitle(), request.getContent())
                .map(PostResponseV2::of);
    }

    @GetMapping("")
    public Flux<PostResponseV2> findAllPost() {
        return postsServiceV2.findAll().map(PostResponseV2::of);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PostResponseV2>> findPost(@PathVariable Long id) {
        return postsServiceV2.findById(id)
                .map(p -> ResponseEntity.ok().body(PostResponseV2.of(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> deletePost(@PathVariable Long id) {
        return postsServiceV2.findById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
