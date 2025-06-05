package com.hoangHocDev.Dolas_Pharmarcy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponse {
    String ward;
    String district;
    String province;
    String phoneNumber;

    boolean isPrimary;
}
