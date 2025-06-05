package com.hoangHocDev.Dolas_Pharmarcy.service;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.OrderCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.OrderUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.OrderResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
public interface OrderService{
    OrderResponse createOrder(OrderCreationRequest request);
    OrderResponse updateOrder(String orderId, OrderUpdateRequest request);
    Page<OrderResponse> findOrderByPage(int page, int size);
    OrderResponse findOrderById(String orderId);
    void deleteOrder(String orderId);
}
