package com.hoangHocDev.Dolas_Pharmarcy.repository;

import com.hoangHocDev.Dolas_Pharmarcy.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
