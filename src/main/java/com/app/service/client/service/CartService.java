package com.app.service.client.service;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.domain.cart.CartDTO;
import com.app.service.client.domain.cart.CartRequestDTO;
import com.app.service.client.model.Cart;

public interface CartService {

    Cart findById(Long id);

    Cart findCartByCustomer(Long customerId) throws ValidateException;

    CartDTO addToCart(CartRequestDTO cartRequestDTO) throws ValidateException;

    CartDTO removeItemFromCart(CartRequestDTO cartRequestDTO) throws ValidateException;

    CartDTO updateItemFromCart(CartRequestDTO cartRequestDTO) throws ValidateException;

    String emptyCart(CartRequestDTO cartRequestDTO) throws ValidateException;

    CartDTO viewCart(Long customerId) throws ValidateException;

}
