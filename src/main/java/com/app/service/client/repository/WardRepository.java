package com.app.service.client.repository;
import com.app.service.client.model.Ward;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WardRepository extends JpaRepository<Ward, Long> {
    @Query(value = "select * from ward where district_id =:districtId", nativeQuery = true)
    List<Ward> findByDistrict(Long districtId);
    @Query(value = "select * from ward where ward_code =:wardCode", nativeQuery = true)
    Ward getWardByCode(@Param("wardCode") String wardCode);
}