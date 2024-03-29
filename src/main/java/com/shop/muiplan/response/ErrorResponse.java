package com.shop.muiplan.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다.",
 *     "validation": {
 *         "itemName": "아이템 이름을 입력해주세요.",
 *         "itemPrice": "아이템 가격을 입력해주세요."
 *     }
 * }
 */
@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    private final Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
