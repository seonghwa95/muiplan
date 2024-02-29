package com.shop.muiplan.response;

import com.shop.muiplan.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class PostResponse {

    private final Long id;
    private final String itemName;
    private final Integer itemPrice;

    // 생성자 오버로딩
    public PostResponse(Item item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
    }

    @Builder
    public PostResponse(Long id, String itemName, Integer itemPrice) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
