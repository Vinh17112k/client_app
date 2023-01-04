package com.app.service.client.service.impl;

import com.app.service.client.model.Province;
import com.app.service.client.repository.ProvinceRepository;
import com.app.service.client.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Override
    public List<Province> listProvince() {
        return provinceRepository.findAll();
    }
}
