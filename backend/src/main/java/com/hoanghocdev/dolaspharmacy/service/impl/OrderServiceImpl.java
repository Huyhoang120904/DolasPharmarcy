package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.entity.notification.Notification;
import com.hoanghocdev.dolaspharmacy.entity.notification.enums.NotificationType;
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
import com.hoanghocdev.dolaspharmacy.repository.OrderRepository;
import com.hoanghocdev.dolaspharmacy.repository.UserDetailRepository;
import com.hoanghocdev.dolaspharmacy.repository.VariantRepository;
import com.hoanghocdev.dolaspharmacy.service.OrderService;
import com.hoanghocdev.dolaspharmacy.service.PromotionService;
import com.hoanghocdev.dolaspharmacy.service.UserEntityService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderMapper orderMapper;
    VariantRepository variantRepository;
    OrderRepository orderRepository;
    UserDetailRepository userDetailRepository;
    UserEntityService userEntityService;
    PromotionService promotionService;
    EmailServiceImpl emailService;
    KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreationRequest request) throws IOException {
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

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Notification orderNoti = Notification.builder()
                .notificationType(NotificationType.CREATED)
                .username(username)
                .message("Đơn hàng của bạn đã được tạo với mã " + savedOrder.getId() + "!")
                .build();

        kafkaTemplate.send("ws-notification",  orderNoti);

        emailService.sendOrderConfirmationMail(savedOrder);


        return orderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional
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
    public OrderResponse payOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        order.setOrderStatus(OrderStatus.PAID);
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
        if (promotionFixed != null && promotionPercentage != null) {
            if (promotionFixed.getDiscountAmount() >= promotionPercentage.getDiscountAmount() * order.getTotal()) {
                promotion = promotionFixed;
            } else {
                promotion = promotionPercentage;
            }
        }
        if (promotionFixed == null && promotionPercentage != null) {
            promotion = promotionPercentage;
        }

        if (promotionPercentage == null && promotionFixed != null) {
            promotion = promotionFixed;
        }

        if (promotion != null) {
            order.setPromotion(promotion);
            if (promotion.getPromotionType() == PromotionType.FIXED_AMOUNT_ORDER) {
                order.setTotal(order.getTotal() - promotion.getDiscountAmount());
            }
            if (promotion.getPromotionType() == PromotionType.PERCENTAGE_ORDER) {
                order.setTotal(order.getTotal() * (1 - promotion.getDiscountAmount() / 100));
            }
        }
    }

}
