package com.app.service.client.domain.productType;

import com.app.service.client.domain.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;
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
public class ProductTypeDTO extends BaseDTO {
    private Long id;
    @NotNull(message = "Tên loại sản phẩm")
    private String name;
    private String code;
    private Integer status;
    private String description;
    private Long parentId;
}
