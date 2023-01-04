package com.app.service.client.service.impl;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.model.CartItem;
import com.app.service.client.repository.CartItemRepository;
import com.app.service.client.service.CartItemService;
import com.app.service.client.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private MessageService messageService;

    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public void save(CartItem order) {
        cartItemRepository.save(order);
    }

    @Override
    public CartItem getCartDetail(Long id) throws ValidateException {
        return cartItemRepository.findById(id).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.code.entity.not_found")));
    }

    @Override
    public Boolean delete(List<Long> ids) {
        List<CartItem> cartItems = cartItemRepository.findAllByProducts(ids);
        cartItems.forEach(c -> cartItemRepository.updateIsDeleted(c.getId()));
        return true;
    }
}