package com.app.service.client.model;

import com.app.service.client.model.base.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voucher")
public class Voucher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Long voucherId;
    //    loại khuyến mãi, 1 giảm giá, miễn phí vận chuyển ...
    @Column(name = "type_voucher")
    private Integer typeVoucher;
    private String description;
    private String code;
//    loại khuyến mãi, 1 theo giá, 2 theo phần trăm
    private Integer type;
//    giá giảm
    private Integer discountPrice;
    private LocalDateTime expiredTime;
    private Integer status;
}
