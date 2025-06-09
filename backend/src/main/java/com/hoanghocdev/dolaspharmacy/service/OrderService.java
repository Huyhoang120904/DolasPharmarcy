package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.OrderCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.OrderUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface OrderService{
    OrderResponse createOrder(OrderCreationRequest request);
    OrderResponse updateOrder(String orderId, OrderUpdateRequest request);
    Page<OrderResponse> findOrderByPage(int page, int size);
    OrderResponse findOrderById(String orderId);
    void deleteOrder(String orderId);
}
