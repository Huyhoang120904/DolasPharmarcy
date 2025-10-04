package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.entity.notification.Notification;
import com.hoanghocdev.dolaspharmacy.entity.notification.enums.NotificationType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaConsumerTemplate {
    SimpMessagingTemplate messagingTemplate;


    @KafkaListener(topics = "ws-notification")
    public void listenOrderNotification(Notification notification) {
        log.info("Message listened in kafka: {}", notification);
        if (notification.getNotificationType() == NotificationType.CREATED ||
                notification.getNotificationType() == NotificationType.STATUS_UPDATE) {

            String destination = "/topic/notification";
            log.info("Sending user notification to user: {} at destination: {}",
                    notification.getUsername(), destination);

            messagingTemplate.convertAndSendToUser(
                    notification.getUsername(),
                    destination,
                    notification
            );
        }

        if (notification.getNotificationType() == NotificationType.PROMOTION) {
            log.info("Sending promotion notification to: /topic/notification");
            messagingTemplate.convertAndSend("/topic/notification", notification);
        }

    }
}
