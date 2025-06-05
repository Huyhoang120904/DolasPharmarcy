package com.hoangHocDev.Dolas_Pharmarcy.dto.response;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.AddressRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.OrderCreationRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String fullName;
    LocalDate receiveDate;
    String receiveTime;
    AddressRequest address;
    List<OrderCreationRequest> orderItems;
}
