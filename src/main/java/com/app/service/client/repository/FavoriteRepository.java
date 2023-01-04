package com.app.service.client.repository;

import com.app.service.client.model.Favorite;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query(value = "select * from favorite f where f.customer_id =:userId and f.product_id in :productId and f.is_deleted = 0 ", nativeQuery = true)
    List<Favorite> findByUserAndProduct(@Param("userId") Long userId, @Param("productId") Set<Long> productId);
    @Query(value = "select * from favorite f where f.customer_id =:customerId and f.is_deleted = 0 ", nativeQuery = true)
    List<Favorite> listFavoriveByCustomer(@Param("customerId") Long customerId);

    @Query(value = "select f.product_id from favorite f where f.customer_id =:customerId and f.is_deleted = 0 ", nativeQuery = true)
    List<Long> listProductId(@Param("customerId") Long customerId);
    Boolean existsByProductIdAndCustomerId(Long productId, Long customerId);
}