package com.shop.muiplan.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostCreate {

    private String itemName;
    private Integer itemPrice;
}
