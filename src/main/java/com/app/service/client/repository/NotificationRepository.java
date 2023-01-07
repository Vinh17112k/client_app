package com.app.service.client.repository;

import com.app.service.client.model.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query(value = "select * from notification where type = 2 and (is_read is null or is_read = 0) order by created_date desc limit 20 ", nativeQuery = true)
    List<Notification> listNotifcationCustomer();
}
