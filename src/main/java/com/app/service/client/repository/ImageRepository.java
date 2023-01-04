package com.app.service.client.repository;

import com.app.service.client.model.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query(value = "select pi.file_path from product_image pi where pi.product_id =:productId", nativeQuery = true)
    List<String> findFilePathByProdutId(Long productId);
}