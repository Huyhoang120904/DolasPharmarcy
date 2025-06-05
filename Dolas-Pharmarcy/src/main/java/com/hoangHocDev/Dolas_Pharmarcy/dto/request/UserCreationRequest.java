package com.hoangHocDev.Dolas_Pharmarcy.dto.request;

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

    @Size(min = 5)
    @NotNull
    String username;

    @Size(min = 8)
    @NotNull
    String password;

    String gender;
    String fullName;
    LocalDate dob;
}
