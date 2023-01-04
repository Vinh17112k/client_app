package com.app.service.client.domain.product;

import com.app.service.client.domain.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ProductAttributeDTO extends BaseDTO {
    private String laptopType;
    private String port;
    private String hardDisk;
    private String ram;
    private String cpu;
    private String pin;
    private String inputVoltage;
    private String feature;
    private String cam;
    private String simInput;
    private String inputCharge;
}
