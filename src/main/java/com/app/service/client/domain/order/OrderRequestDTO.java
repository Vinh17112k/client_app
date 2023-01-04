package com.app.service.client.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private Long customerId;
    private Long cartId;
    private Long paymentId;
    private Long shipmentId;
    private Long total;

}
