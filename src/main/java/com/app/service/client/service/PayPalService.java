package com.app.service.client.service;

import com.app.service.client.domain.payment.PayPalResponseDTO;
import com.paypal.api.payments.Payment;

public interface PayPalService {

    /**
     * Creates payment on PayPal
     *
     * @param price amount you want to charge
     * @return payPal url that render PayPal window
     */
    PayPalResponseDTO createPayment(Double price);

    /**
     * Executes payment on PayPal
     *
     * @param paymentId param from successUrl
     * @param payerId   param from successUrl
     * @return execution result
     */
    Payment executePayment(String paymentId, String payerId);
}
