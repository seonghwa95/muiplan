package com.shop.muiplan.controller;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.request.PostCreate;
import com.shop.muiplan.response.PostResponse;
import com.shop.muiplan.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/tests")
    public String get() {
        return "Hello MUIPLAN!";
    }

    @PostMapping("/posts")
    public Item post(@RequestBody @Valid PostCreate request) {

        return postService.itemPost(request);
    }

    @GetMapping("/posts/{id}")
    public PostResponse findItem(@PathVariable Long id) {
        PostResponse response = postService.get(id);

        return response;
    }
}
