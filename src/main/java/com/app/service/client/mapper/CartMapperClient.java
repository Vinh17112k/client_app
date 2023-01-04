package com.app.service.client.mapper;

import com.app.service.client.domain.cart.CartDTO;
import com.app.service.client.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface CartMapperClient {

    CartMapperClient INSTANCE = Mappers.getMapper(CartMapperClient.class);

    CartDTO toDto(Cart cart);
}