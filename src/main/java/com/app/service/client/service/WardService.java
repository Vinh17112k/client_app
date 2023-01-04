package com.app.service.client.service;

import com.app.service.client.model.Ward;

import java.util.List;

public interface WardService {

    List<Ward> listWard(Long districtId);
}
