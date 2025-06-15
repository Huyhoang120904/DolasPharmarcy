package com.hoanghocdev.dolaspharmacy.repository;

import com.hoanghocdev.dolaspharmacy.dto.response.OrderResponse;
import com.hoanghocdev.dolaspharmacy.entity.Order;
import com.hoanghocdev.dolaspharmacy.entity.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findByUserDetail(UserDetail userDetail,Pageable pageable);
}
