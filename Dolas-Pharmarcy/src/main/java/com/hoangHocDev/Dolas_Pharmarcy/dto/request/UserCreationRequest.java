package com.hoangHocDev.Dolas_Pharmarcy.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 5)
    @NotNull
    String username;

    //work: custom validation in the future
    @Size(min = 8)
    @NotNull
    String password;
}
