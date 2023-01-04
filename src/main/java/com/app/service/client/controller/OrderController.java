package com.app.service.client.controller;

import com.app.service.client.domain.cancel.CancelDTO;
import com.app.service.client.domain.order.OrderCancelDTO;
import com.app.service.client.domain.order.OrderRequestDTO;
import com.app.service.client.domain.order.OrderResponseDTO;
import com.app.service.client.domain.order_detail.OrderDetailDTO;
import com.app.service.client.domain.payment.PayPalResponseDTO;
import com.app.service.client.repository.CartRepository;
import com.app.service.client.utils.DataUtils;
import com.app.service.client.config.exceptions.GlobalExceptionHandler.ApiResponseData;
import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.service.CartService;
import com.app.service.client.service.OrderService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/customer/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponseData<PayPalResponseDTO>> create(
        @RequestBody OrderRequestDTO orderRequestDTO)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<PayPalResponseDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(orderService.save(orderRequestDTO))
            .build());
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<ApiResponseData<OrderDetailDTO>> orderDetail(
        @PathVariable("id") Long orderId)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<OrderDetailDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(orderService.orderDetailDTO(orderId))
            .build());
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseData<List<OrderResponseDTO>>> orders(
        @RequestParam("customerId") Long customerId)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<List<OrderResponseDTO>>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(orderService.listOrder(customerId))
            .build());
    }

    @PostMapping("cancel")
    public ResponseEntity<ApiResponseData<Boolean>> cancelOrder(@RequestBody CancelDTO cancelDTO)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<Boolean>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(orderService.cancelOrder(cancelDTO))
            .build());
    }

    @GetMapping("/cancel-list")
    public ResponseEntity<ApiResponseData<List<OrderCancelDTO>>> listOrderCancel(
        @RequestParam("customerId") Long customerId)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<List<OrderCancelDTO>>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(orderService.listOrderDetailCancel(customerId))
            .build());
    }
}
