package com.app.service.client.domain.order;

import com.app.service.client.model.Payment;
import com.app.service.client.model.Shipment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private Float subTotal;
    private Float shippingTotal;
    private Float taxRate;
    private Float taxTotal;
    private Float grandTotal;
    private Integer status;
    private Boolean isDeleted;
    private String description;
    private Shipment shipment;
    private Payment payment;
}
