package com.app.service.client.mapper;

import com.app.service.client.domain.cartitem.CartItemDTO;
import com.app.service.client.domain.order_detail.OrderDetailProduct;
import com.app.service.client.model.CartItem;
import com.app.service.client.model.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface OrderDetailMapperClient {

    OrderDetailMapperClient INSTANCE = Mappers.getMapper(OrderDetailMapperClient.class);

    OrderDetailProduct toDto(OrderDetail orderDetail);
}