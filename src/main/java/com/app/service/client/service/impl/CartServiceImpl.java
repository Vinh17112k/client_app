package com.app.service.client.service.impl;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.config.security.SecurityUtilsClient;
import com.app.service.client.domain.cart.CartDTO;
import com.app.service.client.domain.cart.CartRequestDTO;
import com.app.service.client.domain.cartitem.CartItemDTO;
import com.app.service.client.mapper.CartItemMapperClient;
import com.app.service.client.mapper.CartMapperClient;
import com.app.service.client.model.Cart;
import com.app.service.client.model.CartItem;
import com.app.service.client.model.Customer;
import com.app.service.client.model.Product;
import com.app.service.client.repository.*;
import com.app.service.client.service.CartService;
import com.app.service.client.service.MessageService;
import com.app.service.client.utils.DataUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Log4j2
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartMapperClient cartMapperClient;
    @Autowired
    private CartItemMapperClient cartItemMapperClient;
    @Autowired
    private ImageRepository imageRepository;
    private Float TAX_RATE = 5.0F;

    @Override
    public Cart findById(Long id) {
        return cartRepository.findById(id).get();
    }

    @Override
    public Cart findCartByCustomer(Long customerId) throws ValidateException {
        Cart cart = cartRepository.findByCustomer(customerId);
        if (cart == null) {
            Cart shoppingCart = new Cart();

//            shoppingCart.setPaymentMethod("Cash On Delivery");
//            shoppingCart.setShippingMethod("Free Shipping");
            return shoppingCart;
        }
        return cart;
    }

    public Cart findShoppingCart(Customer customer) {
        Cart shoppingCart = cartRepository.findByCustomer(customer.getCustomerId());
        if (shoppingCart == null) {
            shoppingCart = new Cart();

//            shoppingCart.setPaymentMethod("Cash On Delivery");
//            shoppingCart.setShippingMethod("Free Shipping");

            shoppingCart.setCustomer(customer);
            shoppingCart.setCartItemList(new ArrayList<>());

            return shoppingCart;
        }
        return shoppingCart;
    }

    @Override
    @Transactional
    public CartDTO addToCart(CartRequestDTO cartRequestDTO)
        throws ValidateException {
        Float subTotal = 0.0F, totalPrice = 0.0F, totalTax = 0.0F, grandTotal = 0.0F;
        long totalQty = 0;
        Long productId = cartRequestDTO.getProductId();
        Long quantity = cartRequestDTO.getQuantity();
        Long customerId = cartRequestDTO.getCustomerId();
        if (DataUtils.isNull(productId) || DataUtils.isNull(quantity) || DataUtils.isNull(
            customerId)) {
            throw new ValidateException(messageService.getMessage("error.code.empty.data"));
        }
        Customer customer = customerRepository.findById(customerId).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.user.notfound")));
        if (!SecurityUtilsClient.getCustomerId().equals(customer.getCustomerId())) {
            throw new ValidateException(messageService.getMessage("error.user.not.login"));
        }
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.product.notfound")));

        Cart shoppingCart = findShoppingCart(customer);
        CartItem cartItem = findCartItem(shoppingCart, productId);
        List<CartItem> cartItemList = new ArrayList<>();
        if (cartItem == null) {
            cartItem = new CartItem();

            cartItem.setProduct(product);
            cartItem.setCart(shoppingCart);

            //get items list and item in it
            shoppingCart.getCartItemList();
            cartItemList.add(cartItem);

            shoppingCart.setCartItemList(cartItemList);
        }

        cartItem.setOurPrice(product.getPrice());

        //quantity
        totalQty = cartItem.getQuantity() + quantity;
        cartItem.setQuantity(totalQty);

        //total
        totalPrice = product.getPrice() * totalQty;
        cartItem.setTotalPrice(totalPrice);
        Integer totalWeight = 1;
        if (!DataUtils.isNull(product.getWeight())) {
            totalWeight = cartItem.getQuantity().intValue() * product.getWeight();
        }
        cartItem.setTotalWeight(totalWeight);

        //sub total
        subTotal = getSubTotal(shoppingCart);
        shoppingCart.setSubTotal(subTotal);
        //weight total
        totalWeight = getTotalWeight(shoppingCart);
        shoppingCart.setSubTotal(subTotal);
        shoppingCart.setWeightTotal(totalWeight);

        //Tax rate
        shoppingCart.setTaxRate(TAX_RATE);

        //total Tax
        totalTax = (TAX_RATE / 100) * subTotal;
        shoppingCart.setTaxTotal(totalTax);

        //grand total
        grandTotal = subTotal + totalTax;
        shoppingCart.setGrandTotal(grandTotal);

        cartRepository.save(shoppingCart);
        cartItemList.forEach(ci-> cartItemRepository.save(ci));
        return cartMapperClient.toDto(shoppingCart);
    }

    @Override
    public CartDTO removeItemFromCart(CartRequestDTO cartRequestDTO) throws ValidateException {
        Long productId = cartRequestDTO.getProductId();
        Long customerId = cartRequestDTO.getCustomerId();
        if (DataUtils.isNull(productId) || DataUtils.isNull(customerId)) {
            throw new ValidateException(messageService.getMessage("error.code.empty.data"));
        }
        Customer customer = customerRepository.findById(customerId).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.user.notfound")));
        if (!SecurityUtilsClient.getCustomerId().equals(customer.getCustomerId())) {
            throw new ValidateException(messageService.getMessage("error.user.not.login"));
        }
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.product.notfound")));

        Float subTotal = 0.0F, totalPrice = 0.0F, totalTax = 0.0F, grandTotal = 0.0F;
        long totalQty = 0;

        Cart shoppingCart = findShoppingCart(customer);
        CartItem cartItem = findCartItem(shoppingCart, product.getId());

        log.debug("cart" + cartItem);

        List<CartItem> cartItemList = cartItemRepository.findByCartId(shoppingCart.getId());
        log.debug("before size=" + cartItemList.stream().count());

        cartItemList.remove(cartItem);
        log.debug("after size=" + cartItemList.stream().count());

        //set updated cart item
        shoppingCart.setCartItemList(cartItemList);

        //-------------------//

        //sub total
        subTotal = getSubTotal(shoppingCart);
        shoppingCart.setSubTotal(subTotal);
        Integer weightTotal = getTotalWeight(shoppingCart);
        shoppingCart.setWeightTotal(weightTotal);
        //Tax rate
        shoppingCart.setTaxRate(TAX_RATE);

        //total Tax
        totalTax = (TAX_RATE / 100) * subTotal;
        shoppingCart.setTaxTotal(totalTax);

        //grand total
        grandTotal = subTotal + totalTax;
        shoppingCart.setGrandTotal(grandTotal);

        cartRepository.save(shoppingCart);

        //deleted instance passed to merge:

        //Delete child item
        cartItemRepository.delete(cartItem);

        return cartMapperClient.toDto(shoppingCart);
    }

    @Override
    public CartDTO updateItemFromCart(CartRequestDTO cartRequestDTO) throws ValidateException {
        Long productId = cartRequestDTO.getProductId();
        Long quantity = cartRequestDTO.getQuantity();
        Long customerId = cartRequestDTO.getCustomerId();
        if (DataUtils.isNull(productId) || DataUtils.isNull(quantity) || DataUtils.isNull(
            customerId)) {
            throw new ValidateException(messageService.getMessage("error.code.empty.data"));
        }
        Customer customer = customerRepository.findById(customerId).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.user.notfound")));
        if (!SecurityUtilsClient.getCustomerId().equals(customer.getCustomerId())) {
            throw new ValidateException(messageService.getMessage("error.user.not.login"));
        }
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.product.notfound")));
        Float subTotal = 0.0F, totalPrice = 0.0F, totalTax = 0.0F, grandTotal = 0.0F;
        long totalQty = 0;

        Cart shoppingCart = findShoppingCart(customer);
        CartItem cartItem = findCartItem(shoppingCart, product.getId());

        cartItem.setOurPrice(product.getPrice());

        //quantity
        totalQty = quantity;
        cartItem.setQuantity(totalQty);

        //total
        totalPrice = product.getPrice() * totalQty;
        cartItem.setTotalPrice(totalPrice);
        //total weight
        Integer totalWeight = 1;
        if (!DataUtils.isNull(product.getWeight())) {
            totalWeight = cartItem.getQuantity().intValue() * product.getWeight();
        }
        cartItem.setTotalWeight(totalWeight);

        totalWeight = getTotalWeight(shoppingCart);
        shoppingCart.setWeightTotal(totalWeight);

        //-------------------//

        //sub total
        subTotal = getSubTotal(shoppingCart);
        shoppingCart.setSubTotal(subTotal);

        //Tax rate
        shoppingCart.setTaxRate(TAX_RATE);

        //total Tax
        totalTax = (TAX_RATE / 100) * subTotal;
        shoppingCart.setTaxTotal(totalTax);

        //grand total
        grandTotal = subTotal + totalTax;
        shoppingCart.setGrandTotal(grandTotal);

        cartRepository.save(shoppingCart);
        return cartMapperClient.toDto(shoppingCart);
    }

    @Override
    public String emptyCart(CartRequestDTO cartRequestDTO) throws ValidateException {
        Long customerId = cartRequestDTO.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.user.notfound")));
        if (!SecurityUtilsClient.getCustomerId().equals(customer.getCustomerId())) {
            throw new ValidateException(messageService.getMessage("error.user.not.login"));
        }
        Long cartId = customer.getShoppingCart().getId();
        cartItemRepository.deleteByCartId(cartId);
        cartRepository.deleteById(cartId);
        return messageService.getMessage("error.code.cart.empty.success");
    }

    @Transactional
    public CartItem findCartItem(Cart shoppingCart, Long productId) {
        log.debug("Cart item count" + shoppingCart.getCartItemList().stream().count());
        if (!DataUtils.isNull(shoppingCart)) {
            for (CartItem cartItem : shoppingCart.getCartItemList()) {
                //item found
                if (cartItem.getProduct().getId().equals(productId)) {
                    return cartItem;
                }
            }
        }
        return null;
    }

    private Float getSubTotal(Cart shoppingCart) {
        Float subTotal = 0.0F;
        for (CartItem cartItem : shoppingCart.getCartItemList()) {
            subTotal += cartItem.getTotalPrice();
        }
        return subTotal;
    }

    private Integer getTotalWeight(Cart shoppingCart) {
        Integer totalWeight = 0;
        for (CartItem cartItem : shoppingCart.getCartItemList()) {
            if (!DataUtils.isNull(cartItem.getTotalWeight())) {
                totalWeight += cartItem.getTotalWeight();
            }
        }
        return totalWeight;
    }

    @Override
    public CartDTO viewCart(Long customerId) throws ValidateException {

        Cart cart = cartRepository.findByCustomer(customerId);
        if (DataUtils.isNull(cart)) {
            return null;
        } else {
            CartDTO cartDTO = cartMapperClient.toDto(cart);
            List<CartItem> cartItemList = cartItemRepository.findByCartId(cart.getId());
            List<CartItemDTO> cartItemDTOs = cartItemMapperClient.toDtoList(cartItemList);
            List<String> path;
            for (CartItemDTO cartItemDTO : cartItemDTOs) {
                path = imageRepository.findFilePathByProdutId(cartItemDTO.getProduct().getId());
                cartItemDTO.getProduct().setPath(path);
            }
            cartDTO.setCartItemList(cartItemDTOs);
            return cartDTO;
        }
    }
}
