package com.app.service.client.repository;

import com.app.service.client.model.VoucherProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoucherProductRepository extends JpaRepository<VoucherProduct,Long> {

    @Query(value = "select case when exists (select 1 from voucher_product vp "
        + " join product p on vp.product_id = p.product_id "
        + " join voucher v on vp.voucher_id = v.voucher_id where  p.product_id =18 "
        + " and v.type_voucher = 2) then 'true' else 'false' end ", nativeQuery = true)
    Boolean existsVoucherProductByProductIdAndVoucher(Long productId);
}
