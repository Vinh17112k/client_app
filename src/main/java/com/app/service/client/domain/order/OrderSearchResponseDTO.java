package com.app.service.client.domain.order;

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
public class OrderSearchResponseDTO {
    private Long oderId;
    private String customerName;
    private Integer status;
    private Float price;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private Long shippingTotal;
    private Integer isPayment;
}
