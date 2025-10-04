package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.entity.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    Page<Notification> findOrderNotificationByPageAndUsername(Pageable pageable);
}
