package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.PaymentRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.PaymentResponse;
import com.hoanghocdev.dolaspharmacy.service.OrderService;
import com.hoanghocdev.dolaspharmacy.service.impl.VnPayServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    VnPayServiceImpl vnPayService;
    OrderService orderService;

    @Value("${spring.security.cors_url}")
    @NonFinal
    String CORS_URL;

    @PostMapping("/create-payment")
    private ApiResponse<PaymentResponse> pay(HttpServletRequest req, @RequestBody @Valid PaymentRequest request) throws IOException {
        return ApiResponse.<PaymentResponse>builder()
                .result(vnPayService.getUrl(request, req))
                .build();
    }

    @GetMapping("/recall")
    private ApiResponse<PaymentResponse> pay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String status = req.getParameter("vnp_ResponseCode");
        String orderId = req.getParameter("vnp_OrderInfo");
        if (status.equals("00")) {
            resp.sendRedirect(String.format(CORS_URL +
                    "/confirmation/%s", orderId));

            req.getParameterMap().entrySet().forEach(entry ->
                    log.info("request info: {} = {}", entry.getKey(), entry.getValue()));

            orderService.payOrder(orderId);

            return ApiResponse.<PaymentResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .code(0)
                    .message("Payment successes")
                    .build();
        } else {
            //handle filed payment in the future
            vnPayService.handleFailedPayment(req);
            resp.sendRedirect(String.format(CORS_URL +
                    "/confirmation/%s", orderId));
            return ApiResponse.<PaymentResponse>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .code(9999)
                    .message("Payment failed")
                    .build();
        }
    }


}
