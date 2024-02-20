package com.shop.muiplan.controller;

import com.shop.muiplan.request.PostCreate;
import com.shop.muiplan.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<String, String> post(@RequestBody PostCreate request) {

        // 리턴 방식에 대한 수정? 및 이해? 필요
        postService.itemPost(request);

        return Map.of();
    }
}
