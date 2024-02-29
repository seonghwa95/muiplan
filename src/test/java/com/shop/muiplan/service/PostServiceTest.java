package com.shop.muiplan.service;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.repository.PostRepository;
import com.shop.muiplan.request.PostCreate;
import com.shop.muiplan.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 등록")
    void itemPost() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .itemName("화분")
                .itemPrice(58000)
                .build();

        // when
        postService.itemPost(postCreate);

        // then
        assertEquals(1L, postRepository.count());

        Item item = postRepository.findAll().get(0);

        assertEquals("화분", item.getItemName());
        assertEquals(58000, item.getItemPrice());
    }

    @Test
    @DisplayName("상품 상세 조회")
    void getOne() {
        // given
        Item requestItem = Item.builder()
                .itemName("화분")
                .itemPrice(58000)
                .build();
        postRepository.save(requestItem);

        Long itemId = requestItem.getId();

        // when
        PostResponse response = postService.get(itemId);

        // then
        assertNotNull(response);
        assertEquals(1L, postRepository.count());
        assertEquals("화분", response.getItemName());
        assertEquals(58000, response.getItemPrice());
    }

    @Test
    @DisplayName("전체 상품 조회")
    void getAllTest() {
        // given
        Item item1 = Item.builder()
                .itemName("화분")
                .itemPrice(58000)
                .build();

        Item item2 = Item.builder()
                .itemName("식물")
                .itemPrice(4000)
                .build();

        postRepository.save(item1);
        postRepository.save(item2);

        // when
        List<PostResponse> itemList = postService.getList();

        // then
        assertNotNull(itemList);
        assertEquals(2L, itemList.stream().count());
        assertEquals("화분", itemList.get(0).getItemName());
        assertEquals("식물", itemList.get(1).getItemName());
    }
}