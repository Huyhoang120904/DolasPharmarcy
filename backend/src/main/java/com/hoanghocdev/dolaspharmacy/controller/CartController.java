package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.CartRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.OrderCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.CartResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.OrderResponse;
import com.hoanghocdev.dolaspharmacy.service.CartService;
import com.hoanghocdev.dolaspharmacy.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Tag(name = "Cart")
@Validated
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
    public ApiResponse<CartResponse> addOrderItemToCart(@RequestBody @Valid CartRequest request) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.addToCart(request))
                .build();
    }

    @PutMapping("/orderItems/{orderItemId}")
    public ApiResponse<CartResponse> updateOrderItemQuantityToCart(@PathVariable @NotBlank(message = "Cart item must not null") String orderItemId,
                                                                   @Valid CartRequest request) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.updateQuantity(orderItemId, request))
                .build();
    }

    @DeleteMapping("/orderItems/{orderItemId}")
    public ApiResponse<CartResponse> removeOrderItemToCart(@PathVariable @NotBlank(message = "Cart item must not null") String orderItemId) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.removeFromCart(orderItemId))
                .build();
    }

    //Checkout
    @PostMapping("/checkout")
    public ApiResponse<OrderResponse> createOrderFromCart(@RequestBody @Valid OrderCreationRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

}
