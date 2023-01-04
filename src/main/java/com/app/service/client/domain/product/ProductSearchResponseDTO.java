package com.app.service.client.domain.product;

import com.app.service.client.domain.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
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
public class ProductSearchResponseDTO extends BaseDTO {
    private Long productId;
    private String makeName;
    private String name;
    private String code;
    private String description;
    private Integer weight;
    private LocalDateTime expiredDate;
    private Float price;
    private String productType;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Integer status;
    private Integer stockQty;
}
