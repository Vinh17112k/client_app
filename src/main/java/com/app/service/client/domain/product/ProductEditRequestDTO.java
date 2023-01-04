package com.app.service.client.domain.product;

import com.app.service.client.domain.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ProductEditRequestDTO extends BaseDTO {
    private Long id;
    private Long makeId;
    private String name;
    private String code;
    private String description;
    private String packingType;
    private Integer weight;
    private LocalDateTime expiredDate;
    private Float price;
    private String image;
    private String imageSecond;
    private Integer status;
    private Integer stockQty;
    @NotNull(message = "Tên loại sản phẩm")
    private Long productTypeId;
    private ProductAttributeDTO productAttributeDTO;
    private MultipartFile[] path;
    private Integer coreQuantity;
    private String processSpeed;
    private String ram;
    private String card;
    private String screenSize;
    private String screenHd;
    private String hardDisk;
    private String pin;
}
