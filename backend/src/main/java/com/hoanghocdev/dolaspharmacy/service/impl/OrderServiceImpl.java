package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.OrderCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.OrderUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.OrderResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.UserResponse;
import com.hoanghocdev.dolaspharmacy.entity.*;
import com.hoanghocdev.dolaspharmacy.entity.enums.OrderStatus;
import com.hoanghocdev.dolaspharmacy.entity.enums.PromotionType;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.OrderMapper;
import com.hoanghocdev.dolaspharmacy.repository.*;
import com.hoanghocdev.dolaspharmacy.service.OrderService;
import com.hoanghocdev.dolaspharmacy.service.PromotionService;
import com.hoanghocdev.dolaspharmacy.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderMapper orderMapper;
    VariantRepository variantRepository;
    OrderRepository orderRepository;
    UserDetailRepository userDetailRepository;
    UserEntityService userEntityService;
    PromotionService promotionService;
    VnPayServiceImpl vnPayService;


    @Override
    public OrderResponse createOrder(OrderCreationRequest request) {
        UserResponse userResponse = userEntityService.findMyInfo();

        UserDetail userDetail = userDetailRepository.findByUsername(userResponse.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        Order order = orderMapper.toOrder(request);
        List<OrderItem> orderItems = request.getOrderItems().stream().map(orderItem -> {
            Variant variant = variantRepository.findById(orderItem.getVariantId())
                    .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
            return OrderItem.builder()
                    .variant(variant)
                    .quantity(orderItem.getQuantity())
                    .order(order)
                    .build()
                    .calculateFinalPrice();
        }).toList();

        order.setOrderItems(orderItems);
        order.setUserDetail(userDetail);
        order.setOrderStatus(OrderStatus.PENDING);
        order.calculateTotal();
        applyHighestAvailableOrderPromotion(order);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public OrderResponse updateOrder(String orderId, OrderUpdateRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        orderMapper.updateOrder(request, order);
        if (request.getOrderStatus() != null) {
            order.setOrderStatus(OrderStatus.valueOf(request.getOrderStatus()));
        }

        order = orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Override
    public Page<OrderResponse> findOrderByPage(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toResponse);
    }

    @Override
    public Page<OrderResponse> findMyOrders(Pageable pageable) {
        var contextHolder = SecurityContextHolder.getContext();
        String name = contextHolder.getAuthentication().getName();
        UserDetail userDetail = userDetailRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return orderRepository.findByUserDetail(userDetail, pageable)
                .map(orderMapper::toResponse);
    }

    @Override
    public OrderResponse findOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        return orderMapper.toResponse(order);
    }

    @Override
    public void deleteOrder(String orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        orderRepository.deleteById(orderId);
    }

    private void applyHighestAvailableOrderPromotion(Order order) {
        Promotion promotionFixed = promotionService.getHighestAvailablePromotionForOrderByPromotionType(PromotionType.FIXED_AMOUNT_ORDER).orElse(null);
        Promotion promotionPercentage = promotionService.getHighestAvailablePromotionForOrderByPromotionType(PromotionType.PERCENTAGE_ORDER).orElse(null);
        Promotion promotion = promotionFixed;
        if (promotionFixed !=null && promotionPercentage !=null) {
            if (promotionFixed.getDiscountAmount() >= promotionPercentage.getDiscountAmount()*order.getTotal()) {
                promotion = promotionFixed;
            } else {
                promotion = promotionPercentage;
            }
        }
        if (promotionFixed == null && promotionPercentage !=null) {
            promotion = promotionPercentage;
        }

        if ( promotionPercentage ==null && promotionFixed != null) {
            promotion = promotionFixed;
        }

        if (promotion !=null) {
            order.setPromotion(promotion);
            if (promotion.getPromotionType() == PromotionType.FIXED_AMOUNT_ORDER) {
                order.setTotal(order.getTotal() - promotion.getDiscountAmount());
            }
            if (promotion.getPromotionType() == PromotionType.PERCENTAGE_ORDER) {
                order.setTotal(order.getTotal() *(1- promotion.getDiscountAmount()/100));
            }
        }
    }

}
