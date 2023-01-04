package com.app.service.client.controller;

import com.app.service.client.domain.cart.CartDTO;
import com.app.service.client.domain.cart.CartRequestDTO;
import com.app.service.client.repository.CustomerRepository;
import com.app.service.client.config.exceptions.GlobalExceptionHandler.ApiResponseData;
import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.config.security.SecurityUtilsClient;
import com.app.service.client.service.CartService;
import com.app.service.client.service.MessageService;
import com.app.service.client.service.ProductClientService;
import com.app.service.client.utils.DataUtils;
import java.util.Arrays;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/customer/cart")
@Builder
public class CartController {

    @Autowired
    private ProductClientService productService;
    @Autowired
    private CartService cartService;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final MessageService messageService;

    @GetMapping("/view-cart")
    public ResponseEntity<ApiResponseData<CartDTO>> viewCart()
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<CartDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(cartService.viewCart(SecurityUtilsClient.getCustomerId()))
            .build());
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<ApiResponseData<CartDTO>> addToCart(
        @RequestBody CartRequestDTO cartRequestDTO)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<CartDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(cartService.addToCart(cartRequestDTO))
            .build());
    }

    @PostMapping("/empty-cart")
    public ResponseEntity<ApiResponseData<String>> emptyCart(
        @RequestBody CartRequestDTO cartRequestDTO)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<String>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(cartService.emptyCart(cartRequestDTO))
            .build());
    }

    @PostMapping("/update-cart")
    public ResponseEntity<ApiResponseData<CartDTO>> updateCart(
        @RequestBody CartRequestDTO cartRequestDTO)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<CartDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(cartService.updateItemFromCart(cartRequestDTO))
            .build());
    }

    @PostMapping("/remove-cart")
    public ResponseEntity<ApiResponseData<CartDTO>> remove(
        @RequestBody CartRequestDTO cartRequestDTO)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<CartDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(cartService.removeItemFromCart(cartRequestDTO))
            .build());
    }
}
//    xem co the dung lai api khac khong
//    @GetMapping("/checkout")
//    public ResponseEntity<ApiResponseData<Cart>> remove(@RequestParam("productId") Long productId)
//        throws ValidateException {
//        return ResponseEntity.ok(ApiResponseData.<Cart>builder()
//            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
//            .messages(Arrays.asList("success"))
//            .data(cartService.removeItemFromCart(productId))
//            .build());
//    }