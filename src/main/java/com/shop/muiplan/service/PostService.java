package com.shop.muiplan.service;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.repository.PostRepository;
import com.shop.muiplan.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void itemPost(PostCreate postCreate) {

        Item item = Item.builder()
                .itemName(postCreate.getItemName())
                .itemPrice(postCreate.getItemPrice())
                .build();

        postRepository.save(item);
    }
}
