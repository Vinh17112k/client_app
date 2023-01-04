package com.app.service.client.domain.product;

import com.app.service.client.domain.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
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
public class ProductAddRequestDTO extends BaseDTO {
    private Long makeId;
    @NotNull(message = "Tên sản phẩm")
    @NotBlank(message = "Tên sản phẩm")
    private String name;
    @NotNull(message = "Mã sản phẩm")
    @NotBlank(message = "Mã sản phẩm")
    private String code;
    private String description;
    private String packingType;
    @NotNull(message = "Khối lượng sản phẩm")
    private Integer weight;
    private LocalDateTime expiredDate;
    @NotNull(message = "Giá sản phẩm")
    private Float price;
    private String image;
    private String imageSecond;
    private Integer status;
    @NotNull(message = "Số lượng sản phẩm")
    private Integer stockQty;
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
    private String operationSystem;
}
