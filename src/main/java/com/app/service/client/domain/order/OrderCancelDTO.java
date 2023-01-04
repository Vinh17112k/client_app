package com.app.service.client.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelDTO {
    private String employee;
    private String customerName;
    private String type;
    private String reason;
    private String productName;
}
