package com.app.service.client.domain.productType;

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
public class ProductTypeSearchResponseDTO extends BaseDTO {
    private Long id;
    private String name;
    private String code;
    private String parentName;
    private String createdBy;
    private String description;
    private Integer status;
    private LocalDateTime lastModifiedDate;
}
