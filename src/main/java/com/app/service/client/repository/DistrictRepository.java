package com.app.service.client.repository;

import com.app.service.client.model.District;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DistrictRepository extends JpaRepository<District, Long> {
    @Query(value = "select * from district d where d.province_id =:provinceId", nativeQuery = true)
    List<District> findByProvinceID(Long provinceId);
    @Query(value = "select d.district_id from district d ", nativeQuery = true)
    List<Long> getListDistrictId();
}