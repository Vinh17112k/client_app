package com.app.service.client.repository;

import com.app.service.client.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query(value = "select * from order_detail od where od.is_deleted = 0 and od.order_id =:orderId", nativeQuery = true)
    List<OrderDetail> getOrderDetailsByOrderId(@Param("orderId") Long orderId);
    @Query(value = "select * from order_detail od where od.is_deleted = 1 and od.order_id =:orderId", nativeQuery = true)
    List<OrderDetail> getOrderDetailsCancelByOrderId(@Param("orderId") Long orderId);

    @Query(value = "select * from order_detail od where od.is_deleted = 0 and od.order_id =:orderId and od.product_id =:productId", nativeQuery = true)
    OrderDetail getOrderDetailsByOrderIdAndProduct(@Param("orderId") Long orderId,@Param("productId") Long productId);
}