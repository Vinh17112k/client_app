package com.app.service.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentSuggestionEnum {
    COMMENT_ALL("all"),
    COMMENT_USER("user"),
    COMMENT_PRODUCT("name product"),
    COMMENT_CUSTOMER("customer ");
    private final String value;
}
