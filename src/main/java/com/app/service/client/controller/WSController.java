package com.app.service.client.controller;

import com.app.service.client.config.exceptions.GlobalExceptionHandler.ApiResponseData;
import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.model.District;
import com.app.service.client.model.Province;
import com.app.service.client.model.Ward;
import com.app.service.client.utils.DataUtils;
import com.app.service.client.service.DistrictService;
import com.app.service.client.service.ProvinceService;
import com.app.service.client.service.WardService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/web-service")
@RequiredArgsConstructor
public class WSController {
    private final ProvinceService provinceService;
    private final DistrictService districtService;
    private final WardService wardService;
    @GetMapping("province/list")
    public ResponseEntity<ApiResponseData<List<Province>>> provinces(){
        return ResponseEntity.ok(ApiResponseData.<List<Province>>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(provinceService.listProvince())
            .build());
    }
    @GetMapping("district/list")
    public ResponseEntity<ApiResponseData<List<District>>> districts(@RequestParam("provinceId") Long provinceId){
        return ResponseEntity.ok(ApiResponseData.<List<District>>builder()
                .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
                .messages(Arrays.asList("success"))
                .data(districtService.listDistrict(provinceId))
                .build());
    }
    @GetMapping("ward/list")
    public ResponseEntity<ApiResponseData<List<Ward>>> wards(@RequestParam("districtId") Long districtId)
            throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<List<Ward>>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(wardService.listWard(districtId))
            .build());
    }
}
