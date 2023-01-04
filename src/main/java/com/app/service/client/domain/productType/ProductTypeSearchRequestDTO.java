package com.app.service.client.domain.productType;

import com.app.service.client.domain.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ProductTypeSearchRequestDTO extends BaseDTO {
    private String name;
    private String code;
    private Integer status;
    private Long parentId;
}
