package com.app.service.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderSuggestionEnum {
    ORDER_ALL("all"),
    ORDER_CUSTOMER("customer"),
    ORDER_DATE("date order");
    private final String value;
}
