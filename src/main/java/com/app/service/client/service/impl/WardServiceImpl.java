package com.app.service.client.service.impl;

import com.app.service.client.model.Ward;
import com.app.service.client.repository.WardRepository;
import com.app.service.client.service.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WardServiceImpl implements WardService {
    private final WardRepository wardRepository;

    @Override
    public List<Ward> listWard(Long districtId) {
        return wardRepository.findByDistrict(districtId);
    }
}
