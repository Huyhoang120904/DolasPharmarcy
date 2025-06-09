package com.hoanghocdev.dolaspharmacy.dto.request;

import com.hoanghocdev.dolaspharmacy.validation.ValidVietnamesePhone;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplierRequest {
    @NotBlank(message = "Supplier name is required")
    @Size(min = 2, max = 100, message = "Supplier name must be between 2 and 100 characters")
    String supplierName;

    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 20, message = "Code must be between 2 and 20 characters")
    String code;

    @Size(max = 100, message = "Contact name cannot exceed 100 characters")
    String contactName;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    String email;

    @ValidVietnamesePhone
    String phone;

    @Size(max = 200, message = "Website URL cannot exceed 200 characters")
    String website;

    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Active status must be either ACTIVE or INACTIVE")
    String active;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description;
}
