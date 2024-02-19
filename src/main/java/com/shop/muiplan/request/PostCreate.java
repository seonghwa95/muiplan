package com.shop.muiplan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostCreate {

    private String itemName;
    private Integer itemPrice;

    @Builder
    public PostCreate(String itemName, Integer itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
