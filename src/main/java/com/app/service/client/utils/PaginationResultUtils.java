package com.app.service.client.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResultUtils<E> implements Serializable  {
  private static final long serialVersionUID = 1905122041950251207L;
  private int totalRecords;
  private List<E> listRecord;
}
