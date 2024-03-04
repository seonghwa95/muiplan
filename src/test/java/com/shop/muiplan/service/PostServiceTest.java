package com.shop.muiplan.service;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.repository.PostRepository;
import com.shop.muiplan.request.PostCreate;
import com.shop.muiplan.response.PostResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

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
        postRepository.saveAll(List.of(
                Item.builder()
                        .itemName("화분")
                        .itemPrice(58000)
                        .build(),
                Item.builder()
                        .itemName("식물")
                        .itemPrice(400)
                        .build()
        ));

        // when
        List<PostResponse> itemList = postService.getList();

        // then
        assertNotNull(itemList);
        assertEquals(2L, itemList.stream().count());
        assertEquals("화분", itemList.get(0).getItemName());
        assertEquals("식물", itemList.get(1).getItemName());
    }

    @Test
    @DisplayName("상품 1페이지 조회 (Pageable)")
    void getOnePageItem() {
        // given
        List<Item> request = IntStream.range(1, 31)
                .mapToObj(i -> (
                    Item.builder()
                            .itemName("화분 " + i)
                            .itemPrice(58000 + i)
                            .build()))
                .collect(Collectors.toList());
        postRepository.saveAll(request);

        Pageable pageable = PageRequest.of(0, 5, Sort.by(DESC, "id"));
        // when
        List<PostResponse> posts = postService.getPageList(pageable);

        // then
        assertEquals(5L, posts.size());
        assertEquals(30L, posts.get(0).getId());
        assertEquals("화분 30", posts.get(0).getItemName());

        assertEquals(26L, posts.get(4).getId());
        assertEquals("화분 26", posts.get(4).getItemName());
    }

    @Test
    @DisplayName("상품 1페이지 조회 (QueryDsl)")
    void getOnePageItemQueryDsl() {
        // given
        List<Item> request = IntStream.range(1, 21)
                .mapToObj(i -> (
                    Item.builder()
                            .itemName("화분 " + i)
                            .itemPrice(58000 + i)
                            .build()))
                .collect(Collectors.toList());
        postRepository.saveAll(request);

        // when
        List<PostResponse> posts = postService.getPageList(1);

        // then
        assertEquals(10L, posts.size());
        assertEquals("화분 20", posts.get(0).getItemName());

        assertEquals("화분 11", posts.get(9).getItemName());
    }
}