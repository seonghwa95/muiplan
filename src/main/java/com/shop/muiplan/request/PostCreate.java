package com.shop.muiplan.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PostCreate {

    private String itemName;
    private Integer itemPrice;
}
