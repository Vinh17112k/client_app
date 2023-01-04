package com.app.service.client.domain.district;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class DistrictDTO {
    private Long DistrictID;
    private Long ProvinceID;
    private String DistrictName;
    private Long CountryID;
    private String Code;
    private String SupportType;
    private String Type;
    private Integer Status = 1;
}
