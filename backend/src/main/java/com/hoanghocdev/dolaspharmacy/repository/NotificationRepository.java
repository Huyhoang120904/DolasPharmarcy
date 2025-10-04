package com.hoanghocdev.dolaspharmacy.repository;

import com.hoanghocdev.dolaspharmacy.entity.notification.Notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    @Query("select n from Notification n where n.username  = :username or n.username IS NULL or n.username = '' ")
    Page<Notification> findByUsername(String username, Pageable pageable);
}
