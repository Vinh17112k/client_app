package com.app.service.client.repository;

import com.app.service.client.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher,Long>{

    @Query(value = "select * from voucher v join voucher_product vp on v.voucher_id = vp.voucher_id join product p " +
            " on vp.product_id  = p.product_id " +
            " where v.is_deleted = 0 and p.product_id =:productId" , nativeQuery = true)
    List<Voucher> getVoucherProduct(@Param("productId") Long productId);
}
