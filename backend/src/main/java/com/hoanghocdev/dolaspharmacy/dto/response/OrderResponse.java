package com.hoanghocdev.dolaspharmacy.dto.response;

import com.hoanghocdev.dolaspharmacy.dto.request.AddressRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.OrderCreationRequest;
import com.hoanghocdev.dolaspharmacy.entity.Address;
import com.hoanghocdev.dolaspharmacy.entity.OrderItem;
import com.hoanghocdev.dolaspharmacy.entity.Promotion;
import com.hoanghocdev.dolaspharmacy.entity.UserDetail;
import com.hoanghocdev.dolaspharmacy.entity.enums.OrderStatus;
import com.hoanghocdev.dolaspharmacy.entity.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    String fullName;
    LocalDate receiveDate;
    LocalDateTime createAt;
    String receiveTime;
    UserDetailResponse userDetail;
    AddressRequest address;
    PaymentMethod paymentMethod;
    List<OrderItemResponse> orderItems;
    double total;
    double tax;
    OrderStatus orderStatus;
}
