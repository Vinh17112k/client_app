package com.app.service.client.domain.product;

import com.app.service.client.domain.base.BaseDTO;
import com.app.service.client.domain.make.MakeDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ProductSuggestionDTO extends BaseDTO {
    private List<String> createdBy;
    private List<String> name;
    private List<String> code;
    private List<ProductTypeDTO> productTypeDTOS;
    private List<MakeDTO> makeDTOS;
}
