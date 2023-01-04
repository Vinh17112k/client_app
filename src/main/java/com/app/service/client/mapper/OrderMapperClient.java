package com.app.service.client.mapper;

import com.app.service.client.domain.order.OrderResponseDTO;
import com.app.service.client.domain.order_detail.OrderDetailProduct;
import com.app.service.client.model.Order;
import com.app.service.client.model.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface OrderMapperClient {

    OrderMapperClient INSTANCE = Mappers.getMapper(OrderMapperClient.class);

    OrderResponseDTO toDto(Order order);
}