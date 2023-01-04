package com.app.service.client.repository;
import com.app.service.client.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "select * from cart c join cart_item ci on ci.cart_id = c.cart_id where c.customer_id =:customerId and ci.is_deleted = 0 and c.is_deleted = 0", nativeQuery = true)
    Cart findByCustomer(Long customerId);
    @Modifying
    @Query(value = "delete from cart c where c.cart_id =:cartId", nativeQuery = true)
    void deletedByCartId(@Param("cartId") Long cartId);
}