package com.app.service.client.domain.product;

import com.app.service.client.enums.ProductSuggestionEnum;
import com.app.service.client.domain.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ProductSearchRequestDTO extends BaseDTO {
    private ProductSuggestionEnum enums;
    private String name;
    private String code;
    private Long productTypeId;
    private Integer status;
    private Long makeId;
    private String packingType;
    private Long fromPrice;
    private Long toPrice;
    private String createdBy;
    private LocalDateTime expiredDate;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long fromStockQty;
    private Long toStockQty;
    private List<Long> brandId;
    private List<Long> price;
    private List<Long> categoryId;
    private List<Long> star;
}
