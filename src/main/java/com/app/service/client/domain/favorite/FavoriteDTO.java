package com.app.service.client.domain.favorite;

import com.app.service.client.domain.base.BaseDTO;
import com.app.service.client.domain.customer.CustomerDTO;
import com.app.service.client.domain.product.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class FavoriteDTO extends BaseDTO {
    private CustomerDTO customerDTO;
    private List<ProductDTO> productDTOS;
}
