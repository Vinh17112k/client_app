package com.app.service.client.domain.suggestion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MakeSuggestionEnum {
    MAKE_ALL("all"),
    MAKE_CODE("all"),
    MAKE_NAME("name"),
    MAKE_CREATED("createdBy");
    private final String value;
}
