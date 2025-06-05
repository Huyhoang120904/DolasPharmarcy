package com.hoangHocDev.Dolas_Pharmarcy.controller;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.OrderCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.OrderUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.OrderResponse;
import com.hoangHocDev.Dolas_Pharmarcy.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
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
                                                  @RequestBody OrderUpdateRequest request){
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
