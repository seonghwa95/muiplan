package com.shop.muiplan.controller;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.request.ItemCreate;
import com.shop.muiplan.request.ItemEdit;
import com.shop.muiplan.request.ItemSearch;
import com.shop.muiplan.response.ItemResponse;
import com.shop.muiplan.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/tests")
    public String get() {
        return "Hello MUIPLAN!";
    }

    @PostMapping("/posts")
    public Item post(@RequestBody @Valid ItemCreate request) {

        return itemService.itemPost(request);
    }

    @GetMapping("/posts/{id}")
    public ItemResponse findItem(@PathVariable Long id) {
        ItemResponse response = itemService.get(id);

        return response;
    }

    @GetMapping("/itemsx")
    public List<ItemResponse> findItems() {
         return itemService.getList();
    }

    @GetMapping("/items")
    public List<ItemResponse> findItemsByPage(@ModelAttribute ItemSearch itemSearch) {
        return itemService.getPageList(itemSearch);
    }

    @PatchMapping("/items/{id}")
    public Item edit(@PathVariable Long id, @RequestBody @Valid ItemEdit itemEdit) {
        // 만약 프론트에서 변경하지 않은 기존 내용은 null로 보낸다면??
        return itemService.edit(id, itemEdit);
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.delete(id);
    }

}
