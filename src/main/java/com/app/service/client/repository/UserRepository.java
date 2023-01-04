package com.app.service.client.repository;

import com.app.service.client.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query(value = "select distinct u.username from user u where  u.is_deleted = 0 and u.user_id =:userId ", nativeQuery = true)
  String getUserName(@Param("userId") Long userId);
}
