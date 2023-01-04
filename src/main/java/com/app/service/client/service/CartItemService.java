package com.app.service.client.service;


import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.model.CartItem;

import java.util.List;

public interface CartItemService {

    List<CartItem> findAll();

    void save(CartItem order);

    CartItem getCartDetail(Long id) throws ValidateException;

    Boolean delete(List<Long> productIds);
}
