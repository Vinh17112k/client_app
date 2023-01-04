package com.app.service.client.domain.suggestion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderSuggestionEnum {
    ORDER_ALL("all"),
    ORDER_CUSTOMER("user");
    private final String value;
}
