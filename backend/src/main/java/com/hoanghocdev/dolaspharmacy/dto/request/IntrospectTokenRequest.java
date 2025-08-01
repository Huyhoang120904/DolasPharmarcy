package com.hoanghocdev.dolaspharmacy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectTokenRequest {
    @NotBlank(message = "Token should not be null")
    String token;
}
