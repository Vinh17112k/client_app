package com.app.service.client.domain.order_detail;

import com.app.service.client.model.Order;
import com.app.service.client.model.OrderDetail;
import com.app.service.client.model.Payment;
import com.app.service.client.model.Shipment;
import com.app.service.client.domain.address.AddressDTO;
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
public class OrderDetailDTO {
    private CustomerDTO customerDTO;
    private AddressDTO addressDTO;
    private List<OrderDetail> orderItem;
    private List<ProductDTO> productDTOS;
    private Order order;
    private Payment payment;
    private Shipment shipment;
}
