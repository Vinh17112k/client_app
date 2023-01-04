package com.app.service.client.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayPalResponseDTO {

    private String paypalUrl;
    private Boolean isPaypal;
}
