package com.app.service.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductSuggestionEnum {
    PRODUCT_ALL("all"),
    PRODUCT_CODE("all"),
    PRODUCT_NAME("name"),
    PRODUCT_CREATED("createdBy"),
    PRODUCT_TYPE("type product"),
    PRODUCT_TOP("top order product"),
    PRODUCT_LASTEST("lastest product"),
    PRODUCT_BY_TYPE("type of product"),
    PRODUCT_MULTI_SEARCH("type of product"),
    PRODUCT_MAKE("make ");
    private final String value;
}
