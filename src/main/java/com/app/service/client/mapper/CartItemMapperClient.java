package com.app.service.client.mapper;

import com.app.service.client.domain.cart.CartDTO;
import com.app.service.client.domain.cartitem.CartItemDTO;
import com.app.service.client.model.Cart;
import com.app.service.client.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface CartItemMapperClient {

    CartItemMapperClient INSTANCE = Mappers.getMapper(CartItemMapperClient.class);

    CartItemDTO toDto(CartItem cartItem);
    CartItem toEntity(CartItemDTO cartItemDTO);
    List<CartItemDTO> toDtoList(List<CartItem> cartItem);
}