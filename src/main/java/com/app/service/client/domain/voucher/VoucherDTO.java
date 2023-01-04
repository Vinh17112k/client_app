package com.app.service.client.domain.voucher;

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
public class VoucherDTO {
    //Loại khuyến mại 1: giảm giá sản phẩm, 2: các loại khuyến mãi khác như giảm 10% khi thanh toán paypal, miễn phí vận chuyển
    private Integer typeVoucher;
    //mã khuyến mại
    private String code;
    private String description;
    //số ngày hiệu lực
    private LocalDateTime expiredDate;
    private Integer status;
    private Integer discountPrice;
//    theo giá hay theo % 1 theo % 2 theo giá
    private Integer type;
    private Long voucherId;
    private List<Long> productId;
}
