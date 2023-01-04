package com.app.service.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductTypeSuggestionEnum {
    CODE("code"),
    NAME("name"),
    PARENT("parent"),
    CREATED_BY("createdBy");
    private final String value;
}
