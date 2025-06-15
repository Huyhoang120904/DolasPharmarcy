package com.hoanghocdev.dolaspharmacy.dto.response;

import com.hoanghocdev.dolaspharmacy.dto.request.AddressRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.OrderCreationRequest;
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
    String id;
    String fullName;
    LocalDate receiveDate;
    String receiveTime;
    AddressRequest address;
    List<OrderCreationRequest> orderItems;
}
