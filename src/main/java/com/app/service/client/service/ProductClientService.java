package com.app.service.client.service;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.domain.product.ProductDTO;
import com.app.service.client.domain.product.ProductSearchRequestDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ProductClientService {

    PageImpl<ProductDTO> productClientPage(ProductSearchRequestDTO searchDTO, Pageable pageable)
        throws ValidateException;

    ProductDTO detail(Long id) throws ValidateException;
}
