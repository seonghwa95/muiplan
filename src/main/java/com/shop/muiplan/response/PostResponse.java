package com.shop.muiplan.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class PostResponse {

    private final Long id;
    private final String itemName;
    private final Integer itemPrice;

    @Builder
    public PostResponse(Long id, String itemName, Integer itemPrice) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
