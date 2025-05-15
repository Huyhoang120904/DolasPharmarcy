package com.hoangHocDev.Dolas_Pharmarcy.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Address {

    String ward;
    String district;
    String province;
    String phoneNumber;

    boolean isPrimary;
}
