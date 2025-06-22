package com.hoanghocdev.dolaspharmacy.dto.request;

import com.hoanghocdev.dolaspharmacy.entity.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Full name can only contain letters and spaces")
    String fullName;

    @NotNull(message = "Receive date is required")
    @FutureOrPresent(message = "Receive date cannot be in the past")
    LocalDate receiveDate;

    @NotBlank(message = "Receive time is required")
    String receiveTime;

    @Valid
    AddressRequest address;

    @NotBlank
    String email;

    @NotNull
    PaymentMethod paymentMethod;

    @NotEmpty(message = "Order must contain at least one item")
    @Size(max = 50, message = "Order cannot contain more than 50 items")
    @Valid
    List<OrderItemRequest> orderItems;
}
