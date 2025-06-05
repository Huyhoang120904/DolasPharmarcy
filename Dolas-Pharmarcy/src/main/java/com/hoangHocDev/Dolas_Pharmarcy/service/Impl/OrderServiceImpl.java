package com.hoangHocDev.Dolas_Pharmarcy.service.Impl;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.OrderCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.OrderUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.OrderResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Order;
import com.hoangHocDev.Dolas_Pharmarcy.entity.OrderItem;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Variant;
import com.hoangHocDev.Dolas_Pharmarcy.entity.enums.OrderStatus;
import com.hoangHocDev.Dolas_Pharmarcy.exception.AppException;
import com.hoangHocDev.Dolas_Pharmarcy.exception.ErrorCode;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.OrderMapper;
import com.hoangHocDev.Dolas_Pharmarcy.repository.CartRepository;
import com.hoangHocDev.Dolas_Pharmarcy.repository.OrderRepository;
import com.hoangHocDev.Dolas_Pharmarcy.repository.VariantRepository;
import com.hoangHocDev.Dolas_Pharmarcy.service.CartService;
import com.hoangHocDev.Dolas_Pharmarcy.service.OrderService;
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
