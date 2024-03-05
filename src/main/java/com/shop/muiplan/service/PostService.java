package com.shop.muiplan.service;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.repository.PostRepository;
import com.shop.muiplan.request.PostCreate;
import com.shop.muiplan.request.PostSearch;
import com.shop.muiplan.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public PostResponse get(Long id) {
        // Optional 은 바로 꺼내는 것을 추천
        Item item = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상풉입니다."));

        PostResponse response = PostResponse.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .itemPrice(item.getItemPrice())
                .build();

        return response;
    }

    public List<PostResponse> getList() {
        return postRepository.findAll().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPageList(Pageable pageable) {
        return postRepository.findAll(pageable).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPageList(int page) {
        return postRepository.getList(page).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPageList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
