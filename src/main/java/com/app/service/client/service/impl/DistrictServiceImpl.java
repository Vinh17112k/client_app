package com.app.service.client.service.impl;

import com.app.service.client.model.District;
import com.app.service.client.repository.DistrictRepository;
import com.app.service.client.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    @Override
    public List<District> listDistrict(Long provinceId) {
        return districtRepository.findByProvinceID(provinceId);
    }
}
