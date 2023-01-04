package com.app.service.client.domain.cart;

import com.app.service.client.domain.base.BaseDTO;
import com.app.service.client.domain.cartitem.CartItemDTO;
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
public class CartDTO extends BaseDTO {
    private Long id;
    private Float subTotal;
    private Float taxRate;
    private Float taxTotal;
    private Float grandTotal;
    private Integer weightTotal;
    private List<CartItemDTO> cartItemList;
}
