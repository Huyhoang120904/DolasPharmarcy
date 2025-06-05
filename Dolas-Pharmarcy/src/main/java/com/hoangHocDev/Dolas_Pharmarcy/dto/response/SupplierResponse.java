package com.hoangHocDev.Dolas_Pharmarcy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplierResponse {
    String name;
    String code;
    String contactName;
    String email;
    String phone;
    String website;
    String active;
    String description;
}
