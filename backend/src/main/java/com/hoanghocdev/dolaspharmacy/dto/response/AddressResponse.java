package com.hoanghocdev.dolaspharmacy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponse {
    String id;
    String ward;
    String district;
    String province;
    String phoneNumber;
    String name;
    String zipcode;
    String address;
    boolean primary;
}
