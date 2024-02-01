package com.shop.muiplan.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/posts 요청시 Hello MUIPLAN!을 출력한다.")
    void test() throws Exception {
        mockMvc.perform(get("/tests"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello MUIPLAN!"))
                .andDo(print()); // summary 확인
    }

    @Test
    @DisplayName("/posts 요청시 Hello MUIPLAN!을 출력한다(JSON 검증)")
    void test2() throws Exception {
        mockMvc.perform(post("/tests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemName\":\"화분\", \"itemPrice\": \"58000\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello MUIPLAN!"))
                .andDo(print());
    }
}
