package com.app.service.client.domain.product;

import com.app.service.client.model.Voucher;
import com.app.service.client.domain.base.BaseDTO;
import com.app.service.client.domain.make.MakeDTO;
import com.app.service.client.domain.productType.ProductTypeDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.List;
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
public class ProductDTO extends BaseDTO {
    private Long id;
    private MakeDTO make;
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
    private ProductAttributeDTO productAttribute;
    private ProductTypeDTO productType;
    private List<String> path;
    private List<byte[]> images;
    private Double star;
    private Integer coreQuantity;
    private String processSpeed;
    private String ram;
    private String card;
    private String screenSize;
    private String screenHd;
    private String hardDisk;
    private String pin;
    private float ourPrice;
    private String discount;
    private float totalQuantity;
    private float totalPrice;
    private List<Voucher> voucherList;
}
