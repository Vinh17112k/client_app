package com.app.service.client.service.impl;

import com.app.service.client.config.security.SecurityUtilsClient;
import com.app.service.client.model.Notification;
import com.app.service.client.repository.NotificationRepository;
import com.app.service.client.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    @Override
    public List<Notification> listNotificationCustomer() {
        return notificationRepository.listNotifcationCustomer(SecurityUtilsClient.getCustomerId());
    }
}
