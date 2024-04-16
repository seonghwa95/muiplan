package com.shop.muiplan.controller;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.request.PostCreate;
import com.shop.muiplan.request.PostEdit;
import com.shop.muiplan.request.PostSearch;
import com.shop.muiplan.response.PostResponse;
import com.shop.muiplan.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/itemsx")
    public List<PostResponse> findItems() {
         return postService.getList();
    }

    @GetMapping("/items")
    public List<PostResponse> findItemsByPage(@ModelAttribute PostSearch postSearch) {
        return postService.getPageList(postSearch);
    }

    @PatchMapping("/items/{id}")
    public Item edit(@PathVariable Long id, @RequestBody @Valid PostEdit postEdit) {
        // 만약 프론트에서 변경하지 않은 기존 내용은 null로 보낸다면??
        return postService.edit(id, postEdit);
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        postService.delete(id);
    }

}
