package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.CartRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.CartResponse;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    CartResponse addToCart(CartRequest request);
    CartResponse removeFromCart(String orderItemId);
    void clearCart();
    CartResponse findMyCart();
    CartResponse updateQuantity(String orderItemId, CartRequest request);
}
