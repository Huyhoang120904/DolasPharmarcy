package com.hoanghocdev.dolaspharmacy.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 5, message = "Username must contains at least 5 character")
    @NotNull
    String username;

    @Size(min = 5, message = "Password must contains at least 8 character")
    @NotNull
    String password;

    @Valid
    UserDetailRequest userDetail;
}
