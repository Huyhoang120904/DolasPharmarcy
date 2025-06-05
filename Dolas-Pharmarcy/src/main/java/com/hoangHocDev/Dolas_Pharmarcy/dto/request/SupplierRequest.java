package com.hoangHocDev.Dolas_Pharmarcy.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplierRequest {
    String name;
    String code;
    String contactName;
    String email;
    String phone;
    String website;
    String active;
    String description;
}
