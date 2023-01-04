package com.app.service.client.controller;

import com.app.service.client.repository.CartRepository;
import com.app.service.client.service.PayPalService;
import com.paypal.api.payments.Payment;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/payments")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentsController {

    private final PayPalService payPalService;
    private final CartRepository cartRepository;

    @GetMapping(value = "/paypal/success")
    public Boolean handlePayPalSuccess(@RequestParam(required = false) String token,
        @RequestParam(required = false) String paymentId,
        @RequestParam(required = false) String PayerID) {
        Payment payment = payPalService.executePayment(paymentId, PayerID);
        return true;
        // TODO here you get payment response and can implement other logic by yourself (e.g. saving transaction)
    }

    @GetMapping(value = "/paypal/cancel")
    public void handlePalPalCancel(@RequestParam String token) {
        // TODO here you can handle when user press cancel button on PayPal form
    }

}
