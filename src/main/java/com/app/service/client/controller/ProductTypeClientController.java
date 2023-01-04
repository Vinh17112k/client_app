package com.app.service.client.controller;

import com.app.service.client.config.exceptions.GlobalExceptionHandler.ApiResponseData;
import com.app.service.client.model.ProductType;
import com.app.service.client.utils.DataUtils;
import com.app.service.client.service.ProductTypeClientService;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product-type")
@RequiredArgsConstructor
@Builder
public class ProductTypeClientController {

    private final ProductTypeClientService productTypeClientService;
//    private final MessageService messageService;

    @GetMapping("/get-list")
    public ResponseEntity<ApiResponseData<List<ProductType>>> getList() {
        return ResponseEntity.ok(ApiResponseData.<List<ProductType>>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(productTypeClientService.getList())
            .build());
    }
}
