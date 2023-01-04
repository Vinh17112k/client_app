package com.app.service.client.domain.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherSearchRequestDTO {
    //mã khuyến mại
    private String code;
    private Integer status;
    private Integer type;
}
