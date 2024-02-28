package com.shop.muiplan.service;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.repository.PostRepository;
import com.shop.muiplan.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Item itemPost(PostCreate postCreate) {

        Item item = Item.builder()
                .itemName(postCreate.getItemName())
                .itemPrice(postCreate.getItemPrice())
                .build();

        return postRepository.save(item);
    }

    public Item get(Long id) {
        // Optional 은 바로 꺼내는 것을 추천
        Item item = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상풉입니다."));

        return item;
    }
}
