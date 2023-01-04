package com.app.service.client.controller;

import com.app.service.client.config.exceptions.GlobalExceptionHandler.ApiResponseData;
import com.app.service.client.domain.product.ProductDTO;
import com.app.service.client.domain.product.ProductSearchRequestDTO;
import com.app.service.client.utils.DataUtils;
import com.app.service.client.config.exceptions.GlobalExceptionHandler;
import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.service.ProductClientService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer/product")
public class ProductClientController {

    private final ProductClientService productClientService;

    @GetMapping("detail/{id}")
    public ResponseEntity<ApiResponseData<ProductDTO>> detail(@PathVariable Long id)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<ProductDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(productClientService.detail(id))
            .build());
    }

    @PostMapping("")
    public ResponseEntity<GlobalExceptionHandler.ApiResponseData<PageImpl<ProductDTO>>> listProduct(
        @RequestBody ProductSearchRequestDTO searchDTO, Pageable pageable)
        throws ValidateException, com.app.service.client.config.exceptions.ValidateException {
        return ResponseEntity.ok(
            GlobalExceptionHandler.ApiResponseData.<PageImpl<ProductDTO>>builder()
                .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
                .messages(Arrays.asList("success"))
                .data(productClientService.productClientPage(searchDTO, pageable))
                .build());
    }
}
