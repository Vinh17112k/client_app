package com.app.service.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerSuggestionEnum {
    CUS_ALL("all"),
    CUS_NAME("name"),
    CUS_PHONE("phone"),
    CUS_EMAIL("email"),
    CUS_CODE("code"),
    CUS_FULL_NAME("code");
    private final String value;
}
