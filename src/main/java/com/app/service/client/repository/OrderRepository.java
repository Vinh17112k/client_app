package com.app.service.client.repository;

import com.app.service.client.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select od.product_id from orders o join order_detail od on o.order_id = od.order_id join product p on od.product_id = p.product_id where od.is_deleted = 1 and o.order_id =:orderId ", nativeQuery = true)
    List<Long> findIdFromOrderDetail(@Param("orderId") Long orderId);
    @Query(value = "select * from orders o where o.status in (0,1) and o.is_deleted = 0 and o.customer_id =:customerId ", nativeQuery = true)
    List<Order> listOrder(@Param("customerId") Long customerId);
}