package com.hoanghocdev.dolaspharmacy.entity.notification;

import com.hoanghocdev.dolaspharmacy.entity.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Enumerated(value = EnumType.STRING)
    NotificationType notificationType;

    @CreationTimestamp
    LocalDateTime createAt;

    String message;
    String url;
    String imageUrl;
    String username;
    LocalDate startDate;
    LocalDate endDate;
}
