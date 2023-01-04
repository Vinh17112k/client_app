package com.app.service.client.repository;
import com.app.service.client.model.OrderCancel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderCancelRepository extends JpaRepository<OrderCancel, Long> {
    @Query(value = "select * from order_cancel oc where oc.customer_id =:customerId", nativeQuery = true)
    List<OrderCancel> getOrderCancels(@Param("customerId") Long customerId);

}