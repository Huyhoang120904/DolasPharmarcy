package com.hoangHocDev.Dolas_Pharmarcy.controller;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.CartRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.OrderCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.CartResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.OrderResponse;
import com.hoangHocDev.Dolas_Pharmarcy.service.CartService;
import com.hoangHocDev.Dolas_Pharmarcy.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class CartController {
    CartService cartService;
    OrderService orderService;

    @GetMapping
    public ApiResponse<CartResponse> getCart() {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.findMyCart())
                .build();
    }

    @PostMapping("/orderItems")
    public ApiResponse<CartResponse> addOrderItemToCart(@RequestBody CartRequest request) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.addToCart(request))
                .build();
    }

    @PutMapping("/orderItems/{orderItemId}")
    public ApiResponse<CartResponse> updateOrderItemQuantityToCart(@PathVariable String orderItemId, CartRequest request) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.updateQuantity(orderItemId, request))
                .build();
    }

    @DeleteMapping("/orderItems/{orderItemId}")
    public ApiResponse<CartResponse> removeOrderItemToCart(@PathVariable String orderItemId) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.removeFromCart(orderItemId))
                .build();
    }

    //Checkout
    @PostMapping("/checkout")
    public ApiResponse<OrderResponse> createOrderFromCart(@RequestBody OrderCreationRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

}
