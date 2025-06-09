package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.OrderCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.OrderUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.OrderResponse;
import com.hoanghocdev.dolaspharmacy.entity.Order;
import com.hoanghocdev.dolaspharmacy.entity.OrderItem;
import com.hoanghocdev.dolaspharmacy.entity.Variant;
import com.hoanghocdev.dolaspharmacy.entity.enums.OrderStatus;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.OrderMapper;
import com.hoanghocdev.dolaspharmacy.repository.CartRepository;
import com.hoanghocdev.dolaspharmacy.repository.OrderRepository;
import com.hoanghocdev.dolaspharmacy.repository.VariantRepository;
import com.hoanghocdev.dolaspharmacy.service.CartService;
import com.hoanghocdev.dolaspharmacy.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderMapper orderMapper;
    VariantRepository variantRepository;
    CartService cartService;
    CartRepository cartRepository;
    OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderCreationRequest request) {
        Order order = orderMapper.toOrder(request);
        List<OrderItem> orderItems = request.getOrderItems().stream().map(orderItem -> {
            Variant variant = variantRepository.findById(orderItem.getVariantId())
                    .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
            return OrderItem.builder()
                    .variant(variant)
                    .quantity(orderItem.getQuantity())
                    .order(order)
                    .build().calculateFinalPrice();
        }).toList();
        order.setOrderItems(orderItems);
        cartService.clearCart();
        order.setOrderStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse updateOrder(String orderId, OrderUpdateRequest request) {
        Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        orderMapper.updateOrder(request, order);
        if (request.getOrderStatus()!=null) {
            order.setOrderStatus(OrderStatus.valueOf(request.getOrderStatus()));
        }

        order = orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public Page<OrderResponse> findOrderByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable)
                .map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse findOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }
}
