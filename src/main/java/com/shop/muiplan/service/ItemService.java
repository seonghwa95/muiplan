package com.shop.muiplan.service;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.repository.ItemRepository;
import com.shop.muiplan.request.ItemCreate;
import com.shop.muiplan.request.ItemEdit;
import com.shop.muiplan.request.ItemSearch;
import com.shop.muiplan.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item itemPost(ItemCreate itemCreate) {

        Item item = Item.builder()
                .itemName(itemCreate.getItemName())
                .itemPrice(itemCreate.getItemPrice())
                .build();

        return itemRepository.save(item);
    }

    public ItemResponse get(Long id) {
        // Optional 은 바로 꺼내는 것을 추천
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상풉입니다."));

        ItemResponse response = ItemResponse.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .itemPrice(item.getItemPrice())
                .build();

        return response;
    }

    public List<ItemResponse> getList() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::new)
                .collect(Collectors.toList());
    }

    public List<ItemResponse> getPageList(Pageable pageable) {
        return itemRepository.findAll(pageable).stream()
                .map(ItemResponse::new)
                .collect(Collectors.toList());
    }

    public List<ItemResponse> getPageList(int page) {
        return itemRepository.getList(page).stream()
                .map(ItemResponse::new)
                .collect(Collectors.toList());
    }

    public List<ItemResponse> getPageList(ItemSearch itemSearch) {
        return itemRepository.getList(itemSearch).stream()
                .map(ItemResponse::new)
                .collect(Collectors.toList());
    }

    public Item edit(Long id, ItemEdit itemEdit) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        // 해당 수정 방식에서 파라미터 갯수가 매우 많아질 경우..는 어떻게 할지 고민해보기
        item.change(itemEdit.getItemName(), itemEdit.getItemPrice());

        itemRepository.save(item);

        return item;
    }

    public void delete(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        itemRepository.delete(item);
    }
}
