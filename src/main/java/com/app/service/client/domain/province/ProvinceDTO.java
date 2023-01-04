package com.app.service.client.domain.province;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class ProvinceDTO implements Serializable {
    private Long provinceID;
    private String ProvinceName;
    private Long CountryID;
    private String Code;
    private Integer Status = 1;
    private Boolean CanUpdateCOD;
    private String CreatedAt;
    private Integer isEnable;
    private List<String> NameExtension;
    private Integer RegionID;
    private String UpdatedAt;
    private Integer UpdatedBy;
}
