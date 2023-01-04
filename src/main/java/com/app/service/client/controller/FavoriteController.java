package com.app.service.client.controller;

import com.app.service.client.domain.favorite.FavoriteDTO;
import com.app.service.client.domain.favorite.FavoriteRequestDTO;
import com.app.service.client.utils.DataUtils;
import com.app.service.client.config.exceptions.GlobalExceptionHandler.ApiResponseData;
import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.service.CartService;
import com.app.service.client.service.FavoriteService;
import com.app.service.client.service.OrderService;
import com.app.service.client.service.ProductClientService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/customer/favorite")
public class FavoriteController {
    @Autowired
    private ProductClientService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("add")
    public ResponseEntity<ApiResponseData<Boolean>> addToFavorite(@RequestBody FavoriteRequestDTO favoriteRequestDTO)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<Boolean>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(favoriteService.addToFavorite(favoriteRequestDTO))
            .build());
    }
    @PostMapping("delete")
    public ResponseEntity<ApiResponseData<Boolean>> delete(@RequestBody FavoriteRequestDTO favoriteRequestDTO)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<Boolean>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(favoriteService.delete(favoriteRequestDTO))
            .build());
    }
    @GetMapping("list")
    public ResponseEntity<ApiResponseData<FavoriteDTO>> delete(@RequestParam("customerId") Long customerId)
            throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<FavoriteDTO>builder()
                .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
                .messages(Arrays.asList("success"))
                .data(favoriteService.listFavorite(customerId))
                .build());
    }
}