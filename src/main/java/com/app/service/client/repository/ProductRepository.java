package com.app.service.client.repository;

import com.app.service.client.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    suggestion
    @Query(value="select p from Product p join OrderDetail od on p.id = od.product.id "
        + " join Order o on o.orderId = od.order.orderId "
        + "where 1=1  and p.isDeleted=0  order by p.createdDate ")
    public Page<Product> findAllByActivePage(Pageable pageable);
    @Query(value="select p from Product p where 1=1  and isDeleted=0 order by createdDate ")
    public Page<Product> findNewProduct(Pageable pageable);
    @Query(value="select p from Product p join ProductType pt "
        + " on p.productType.id = pt.id "
        + "where 1=1  and p.isDeleted=0 and pt.id =:productTypeId order by p.createdDate ")
    public Page<Product> findRelativeProduct(@Param("productTypeId") Long productTypeId, Pageable pageable);
    @Query("select p from Product p join p.make m on p.make.id = m.id "
        + " join p.productType pt on p.productType.id = pt.id" +
        " where 1=1 and (:name is null or p.name like concat ('%',:name ,'%' )) " +
        " and (COALESCE(:brandId) is null or m.id in :brandId ) " +
        " and (COALESCE(:categoryId) is null or pt.id in :categoryId ) " +
        " and (:fromPrice is null or p.price >=:fromPrice ) " +
        " and (:toPrice is null or p.price <=:toPrice ) ")
    Page<Product> getListProductIn(
        @Param("brandId") List<Long> brandId,
        @Param("categoryId") List<Long> categoryId,
        @Param("fromPrice") Long fromPrice,
        @Param("toPrice") Long toPrice,
        @Param("name") String name, Pageable pageable);
//    product in order detail
    @Query(value = "select p.* from product p join order_detail o on p.product_id = o.product_id where o.order_id =:orderId", nativeQuery = true)
    List<Product> getProducts(@Param("orderId") Long orderId);
    @Query(value = "select p.* from product p join order_detail o on p.product_id = o.product_id where o.order_id =:orderId and o.order_detail_id=:orderDetailId ", nativeQuery = true)
    Product getProduct(@Param("orderId") Long orderId, @Param("orderDetailId") Long orderDetailId);
    @Query(value = "select p.* from product p where p.is_deleted = 0 and p.product_id in :productId ", nativeQuery = true)
    List<Product> getProducts(@Param("productId") List<Long> productId);

}