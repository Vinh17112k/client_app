package com.app.service.client.domain.voucher;

import com.app.service.client.model.Product;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherSearchResponseDTO {
    //mã khuyến mại
    private Long voucherId;
    private String code;
    private String description;
    private String productApply;
    private List<Product> products;
    private Integer status;
    private LocalDateTime expiredDate;
    private Integer discountPrice;
    private Integer type;
    private Integer typeVoucher;
}
