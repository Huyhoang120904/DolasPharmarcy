package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.OrderCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.OrderUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface OrderService{
    OrderResponse createOrder(OrderCreationRequest request) throws IOException;
    OrderResponse updateOrder(String orderId, OrderUpdateRequest request);
    OrderResponse payOrder(String orderId);
    Page<OrderResponse> findOrderByPage(Pageable pageable);
    Page<OrderResponse> findMyOrders(Pageable pageable);
    OrderResponse findOrderById(String orderId);
    void deleteOrder(String orderId);
}
