package com.hoanghocdev.dolaspharmacy.controller;

import com.cloudinary.Api;
import com.hoanghocdev.dolaspharmacy.dto.request.OrderUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.OrderResponse;
import com.hoanghocdev.dolaspharmacy.entity.Order_;
import com.hoanghocdev.dolaspharmacy.mapper.OrderMapper;
import com.hoanghocdev.dolaspharmacy.service.OrderService;
import com.hoanghocdev.dolaspharmacy.service.impl.EmailServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/orders")
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Order")
@Validated
public class OrderController {
    OrderService orderService;
    EmailServiceImpl emailService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ApiResponse<Page<OrderResponse>> getOrderByPage(@PageableDefault(page = 0, size = 16,
                                                                    sort = Order_.FULL_NAME,
                                                                    direction = Sort.Direction.ASC)
                                                                    Pageable pageable) {
        return ApiResponse.<Page<OrderResponse>>builder()
                .result(orderService.findOrderByPage(pageable))
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
                                                  @RequestBody @Valid OrderUpdateRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrder(orderId, request))
                .build();
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ApiResponse.<OrderResponse>builder()
                .build();
    }

    @PostMapping("/email/{orderId}")
    public ApiResponse orderConfirmation(@PathVariable String orderId) throws IOException {
        OrderResponse response = orderService.findOrderById(orderId);
        emailService.sendOrderConfirmationMail(orderMapper.toOrder(response));
        return ApiResponse.<OrderResponse>builder()
                .build();
    }

}
