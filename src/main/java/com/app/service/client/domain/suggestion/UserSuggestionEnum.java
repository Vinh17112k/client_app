package com.app.service.client.domain.suggestion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserSuggestionEnum {
    USER_ALL("all"),
    USER_NAME("name"),
    USER_PHONE("phone"),
    USER_EMAIL("email"),
    USER_CODE("code"),
    USER_ROLE("role"),
    USER_FULL_NAME("code");
    private final String value;
}
