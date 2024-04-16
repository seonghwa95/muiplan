package com.shop.muiplan.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostEdit {

    @NotBlank(message = "아이템 이름을 입력해주세요.")
    private String itemName;

    @NotNull(message = "아이템 가격을 입력해주세요.")
    private Integer itemPrice;

    @Builder
    public PostEdit(String itemName, Integer itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
