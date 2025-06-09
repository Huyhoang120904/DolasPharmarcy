package com.hoanghocdev.dolaspharmacy.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    String fullName;

    @Future(message = "Receive date must be in the future")
    LocalDate receiveDate;

    @NotBlank(message = "Receive time must not be blank")
    @Size(max = 10, message = "Receive time must not exceed 10 characters")
    String receiveTime;

    @Valid
    AddressRequest address;

    @Size(max = 20, message = "Order status must not exceed 20 characters")
    String orderStatus;
}
