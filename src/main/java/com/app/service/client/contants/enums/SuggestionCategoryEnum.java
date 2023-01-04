package com.app.service.client.contants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuggestionCategoryEnum {
  CATEGORY_NAME("TEN"),
  CATEGORY_CODE("DANH MUC"),
  CATEGORY_PARENT("DANH MUC CHA HOAT DOANG");
  private final String value;
}

