package com.app.service.client.domain.order_detail;

import com.app.service.client.domain.product.ProductDTO;
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
public class OrderDetailProduct {
    private Long orderDetailId;
    private ProductDTO product;
    private Long quantity;
    private Float ourPrice;
    private Float totalPrice;
}
