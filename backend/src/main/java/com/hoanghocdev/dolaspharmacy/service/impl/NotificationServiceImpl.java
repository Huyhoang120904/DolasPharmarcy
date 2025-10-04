package com.hoanghocdev.dolaspharmacy.service.impl;


import com.hoanghocdev.dolaspharmacy.entity.notification.Notification;
import com.hoanghocdev.dolaspharmacy.entity.notification.enums.NotificationType;
import com.hoanghocdev.dolaspharmacy.repository.NotificationRepository;
import com.hoanghocdev.dolaspharmacy.service.NotificationService;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository orderNotificationRepository;

    @Override
    public Page<Notification> findOrderNotificationByPageAndUsername(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderNotificationRepository.findByUsername(username, pageable);
    }


}
