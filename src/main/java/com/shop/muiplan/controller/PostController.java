package com.shop.muiplan.controller;

import com.shop.muiplan.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PostController {

    @GetMapping("/tests")
    public String get() {
        return "Hello MUIPLAN!";
    }

    @PostMapping("/tests")
    public String post(@RequestBody PostCreate postCreate) {
        log.info(postCreate.getItemName());

        return "Hello MUIPLAN!";
    }
}
