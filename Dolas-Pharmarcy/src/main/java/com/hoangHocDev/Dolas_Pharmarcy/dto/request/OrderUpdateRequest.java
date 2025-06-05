package com.hoangHocDev.Dolas_Pharmarcy.dto.request;

import com.hoangHocDev.Dolas_Pharmarcy.entity.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {
    String fullName;
    LocalDate receiveDate;
    String receiveTime;
    AddressRequest address;
    String orderStatus;
}
