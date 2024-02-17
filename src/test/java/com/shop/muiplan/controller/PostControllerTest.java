package com.shop.muiplan.controller;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private PostRepository postRepository;

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
    @DisplayName("/posts 요청시 Hello MUIPLAN!을 출력한다(JSON 검증)")
    void test2() throws Exception {
        // then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemName\":\"화분\", \"itemPrice\": \"58000\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void test3() throws Exception {
        String jsonValue = "{\"itemName\":\"화분\", \"itemPrice\": \"58000\"}";

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
}
