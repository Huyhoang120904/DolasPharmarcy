package com.hoanghocdev.dolaspharmacy.service;


import com.hoanghocdev.dolaspharmacy.dto.response.OrderResponse;
import com.hoanghocdev.dolaspharmacy.entity.Order;
import com.hoanghocdev.dolaspharmacy.mapper.OrderMapper;
import com.hoanghocdev.dolaspharmacy.repository.OrderRepository;
import com.hoanghocdev.dolaspharmacy.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void testSendEmail() throws IOException {
        OrderResponse order = orderService.findOrderById("346efa03-e198-4eb5-a961-c7bab4f81057");
        emailService.sendOrderConfirmationMail(orderMapper. toOrder(order));
    }

}
