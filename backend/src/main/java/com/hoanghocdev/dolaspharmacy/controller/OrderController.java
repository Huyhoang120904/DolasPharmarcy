package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.OrderUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.OrderResponse;
import com.hoanghocdev.dolaspharmacy.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Order")
@Validated
public class OrderController {
    OrderService orderService;

    @GetMapping
    public ApiResponse<Page<OrderResponse>> getOrderByPage(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<OrderResponse>>builder()
                .result(orderService.findOrderByPage(page, size))
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrderByPage(@PathVariable String orderId) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.findOrderById(orderId))
                .build();

    }

    @PutMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(@PathVariable String orderId,
                                                  @RequestBody @Valid OrderUpdateRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrder(orderId,request))
                .build();
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(@PathVariable String orderId){
        orderService.deleteOrder(orderId);
        return ApiResponse.<OrderResponse>builder()
                .build();
    }

}
