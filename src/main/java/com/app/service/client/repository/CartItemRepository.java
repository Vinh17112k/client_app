package com.app.service.client.repository;

import com.app.service.client.model.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = "select * from cart_item ci where ci.is_deleted = 0 and ci.is_active = 1 and ci.product_id in :productIds ", nativeQuery = true)
    List<CartItem> findAllByProducts(@Param("productIds") List<Long> productIds);
    @Query(value = "update column is_deleted value (0) where id =:id", nativeQuery = true)
    void updateIsDeleted(Long id);
    @Modifying
    @Query(value = "delete from cart_item where cart_id =:cartId", nativeQuery = true  )
    void deleteByCartId(Long cartId);
    @Query(value = "select * from cart_item ci where ci.is_deleted = 0 and ci.cart_id =:cartId", nativeQuery = true  )
    List<CartItem> findByCartId(@Param("cartId") Long cartId);
}