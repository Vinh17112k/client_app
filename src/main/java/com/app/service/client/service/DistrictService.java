package com.app.service.client.service;

import com.app.service.client.model.District;

import java.util.List;

public interface DistrictService {
    List<District> listDistrict(Long provinceId);
}
