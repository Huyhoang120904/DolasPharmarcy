package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.CartRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.CartResponse;
import com.hoanghocdev.dolaspharmacy.entity.Cart;
import com.hoanghocdev.dolaspharmacy.entity.OrderItem;
import com.hoanghocdev.dolaspharmacy.entity.Variant;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.CartMapper;
import com.hoanghocdev.dolaspharmacy.repository.CartRepository;
import com.hoanghocdev.dolaspharmacy.repository.OrderItemRepository;
import com.hoanghocdev.dolaspharmacy.repository.VariantRepository;
import com.hoanghocdev.dolaspharmacy.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    CartMapper cartMapper;
    private final VariantRepository variantRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public CartResponse addToCart(CartRequest request) {
        Cart cart = getCart();
        Variant variant = variantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        if (cart.getOrderItems().stream().anyMatch(item -> item.getVariant().getId().equals(variant.getId()))) {
            cart.getOrderItems().stream().filter(item -> item.getVariant().getId().equals(variant.getId()))
                    .forEach(item -> {
                        item.setQuantity(item.getQuantity() + request.getQuantity());
                        item.setFinalPrice(item.calculateFinalPrice().getFinalPrice());
                    });
        } else {
            cart.getOrderItems().add(OrderItem.builder()
                    .variant(variant)
                    .quantity(request.getQuantity())
                    .build().calculateFinalPrice());
        }

        cart = cartRepository.save(cart);
        return cartMapper.toReponse(cart);
    }

    @Override
    public CartResponse removeFromCart(String orderItemId) {
        Cart cart = getCart();

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        cart.getOrderItems().removeIf(item ->
                item.getId().equals(orderItem.getId()));

        cart = cartRepository.save(cart);

        return cartMapper.toReponse(cart);
    }

    @Override
    public void clearCart() {
        Cart cart = getCart();
        cart.setOrderItems(List.of());
        cartRepository.save(cart);
    }

    @Override
    public CartResponse findMyCart() {
        Cart cart = getCart();
        return cartMapper.toReponse(cart);
    }

    @Override
    public CartResponse updateQuantity(String orderItemId, CartRequest request) {
        Cart cart = getCart();

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        cart.getOrderItems().stream().filter(item -> orderItem.getId().equals(item.getId()))
                .forEach(item -> item.setQuantity(request.getQuantity()));

        cart = cartRepository.save(cart);
        return cartMapper.toReponse(cart);
    }

    @PostAuthorize("returnObject.userDetail.userEntity.username == authentication.name")
    public Cart getCart() {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        return  cartRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
    }
}
