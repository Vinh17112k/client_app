package com.app.service.client.service.impl;

import com.app.service.client.model.ProductType;
import com.app.service.client.repository.ProductTypeRepository;
import com.app.service.client.service.ProductTypeClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ProductTypeClientServiceImpl implements ProductTypeClientService {

    private final ProductTypeRepository productTypeRepository;

    @Override
    public List<ProductType> getList() {
        return productTypeRepository.findAll();
    }
}
