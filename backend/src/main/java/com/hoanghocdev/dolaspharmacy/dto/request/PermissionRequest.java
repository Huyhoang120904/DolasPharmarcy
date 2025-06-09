package com.hoanghocdev.dolaspharmacy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRequest {

    @NotBlank(message = "Permission name must not be blank")
    @Size(max = 50, message = "Permission name must not exceed 50 character")
    String permissionName;

    @Size(max = 300, message = "Description must not exceed 300 character")
    String description;

    String role;
}
