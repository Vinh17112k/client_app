package com.app.service.client.service;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.domain.cancel.CancelDTO;
import com.app.service.client.domain.order.OrderCancelDTO;
import com.app.service.client.domain.order.OrderRequestDTO;
import com.app.service.client.domain.order.OrderResponseDTO;
import com.app.service.client.domain.order_detail.OrderDetailDTO;
import com.app.service.client.domain.payment.PayPalResponseDTO;

import java.util.List;

public interface OrderService {
    PayPalResponseDTO save(OrderRequestDTO orderRequestDTO) throws ValidateException;
    OrderDetailDTO orderDetailDTO(Long id) throws ValidateException;
    Boolean cancelOrder(CancelDTO cancelDTO) throws ValidateException;
    List<OrderResponseDTO> listOrder(Long customerId) throws ValidateException;
    List<OrderCancelDTO> listOrderDetailCancel(Long customerId) throws ValidateException;
}
