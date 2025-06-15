package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.OrderCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.OrderUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService{
    OrderResponse createOrder(OrderCreationRequest request);
    OrderResponse updateOrder(String orderId, OrderUpdateRequest request);
    Page<OrderResponse> findOrderByPage(Pageable pageable);
    Page<OrderResponse> findMyOrders(Pageable pageable);
    OrderResponse findOrderById(String orderId);
    void deleteOrder(String orderId);
}
