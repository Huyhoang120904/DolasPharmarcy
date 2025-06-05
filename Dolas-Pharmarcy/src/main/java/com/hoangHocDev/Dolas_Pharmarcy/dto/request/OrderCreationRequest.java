package com.hoangHocDev.Dolas_Pharmarcy.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {
    String fullName;
    LocalDate receiveDate;
    String receiveTime;
    AddressRequest address;
    List<OrderItemRequest> orderItems;
}
