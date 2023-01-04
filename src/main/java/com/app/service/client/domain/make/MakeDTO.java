package com.app.service.client.domain.make;

import com.app.service.client.domain.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotBlank;
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
public class MakeDTO extends BaseDTO {
    private Long id;
    @NotBlank(message = "Tên nhà sản xuất")
    private String name;
    private String code;
    private Integer status;
    private String description;
}
