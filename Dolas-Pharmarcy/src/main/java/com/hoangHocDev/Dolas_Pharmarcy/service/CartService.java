package com.hoangHocDev.Dolas_Pharmarcy.service;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.CartRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.CartResponse;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    CartResponse addToCart(CartRequest request);
    CartResponse removeFromCart(String orderItemId);
    void clearCart();
    CartResponse getMyCart();
    CartResponse updateQuantity(String orderItemId, CartRequest request);
}
