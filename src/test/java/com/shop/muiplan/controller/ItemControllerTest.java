package com.shop.muiplan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.muiplan.domain.Item;
import com.shop.muiplan.repository.ItemRepository;
import com.shop.muiplan.request.ItemCreate;
import com.shop.muiplan.request.ItemEdit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ItemControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository postRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/tests 요청시 Hello MUIPLAN!을 출력한다.")
    void test() throws Exception {
        mockMvc.perform(get("/tests"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello MUIPLAN!"))
                .andDo(print()); // summary 확인
    }

    @Test
    @DisplayName("/posts 요청시 ItemName 값은 필수다.")
    void itemNameValidTest() throws Exception {
        // given
        ItemCreate request = ItemCreate.builder()
                .itemName(null)
                .itemPrice(58000)
                .build();

        String jsonValue = objectMapper.writeValueAsString(request);

        // then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValue)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 Hello MUIPLAN!을 출력한다(JSON 검증)")
    void test2() throws Exception {
        // given
        ItemCreate request = ItemCreate.builder()
                .itemName("화분")
                .itemPrice(58000)
                .build();

        String jsonValue = objectMapper.writeValueAsString(request);

        System.out.println("jsonValue = " + jsonValue);

        // then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValue)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void test3() throws Exception {
        // given
        ItemCreate request = ItemCreate.builder()
                .itemName("화분")
                .itemPrice(58000)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValue)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());

        Item item = postRepository.findAll().get(0);

        assertEquals("화분", item.getItemName());
        assertEquals(58000, item.getItemPrice());
    }

    @Test
    @DisplayName("/posts/{id} 요청시 상품 상세 조히를 실시한다.")
    void getOne() throws Exception {
        // given
        Item item = Item.builder()
                .itemName("화분")
                .itemPrice(58000)
                .build();
        postRepository.save(item);

        // expected
        mockMvc.perform(get("/posts/{id}", item.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.itemName").value(item.getItemName()))
                .andExpect(jsonPath("$.itemPrice").value(item.getItemPrice()))
                .andDo(print());
    }

    @Test
    @DisplayName("/items (GET) 요청시 전체 상품이 조회된다.")
    void getAll() throws Exception {
        // given
        List<Item> request = IntStream.range(1, 21)
                .mapToObj(i -> (
                        Item.builder()
                                .itemName("화분 " + i)
                                .itemPrice(58000 + i)
                                .build()
                ))
                .collect(Collectors.toList());
        postRepository.saveAll(request);

        // expected
        mockMvc.perform(get("/items?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].itemName").value("화분 20"))
                .andExpect(jsonPath("$[0].itemPrice").value(58020))
                .andDo(print());
    }

    @Test
    @DisplayName("/items (GET) 전체 상품 조회시 page가 음수여도 첫페이지가 조회된다.")
    void getAllEvenIfPageZero() throws Exception {
        // given
        List<Item> request = IntStream.range(1, 21)
                .mapToObj(i -> (
                        Item.builder()
                                .itemName("화분 " + i)
                                .itemPrice(58000 + i)
                                .build()
                ))
                .collect(Collectors.toList());
        postRepository.saveAll(request);

        // expected
        mockMvc.perform(get("/items?page=-1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].itemName").value("화분 20"))
                .andExpect(jsonPath("$[0].itemPrice").value(58020))
                .andDo(print());
    }

    @Test
    @DisplayName("/items/{id} (Patch) 요청시 아이템 정보를 수정한다.")
    void putItemNameUpdate() throws Exception {
        // given
        Item item = Item.builder()
                .itemName("화분")
                .itemPrice(58000)
                .build();
        postRepository.save(item);

        ItemEdit itemEdit = ItemEdit.builder()
                .itemName("화분 퍼즐 팟")
                .itemPrice(58000)
                .build();

        // then
        mockMvc.perform(patch("/items/{id}", item.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("/items/{id} (delete) 요청시 아이템 정보를 삭제한다")
    void ItemDelete() throws Exception {
        // given
        Item item = Item.builder()
                .itemName("화분")
                .itemPrice(58000)
                .build();
        postRepository.save(item);

        // expected
        mockMvc.perform(delete("/items/{id}", item.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}
