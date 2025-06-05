package com.hoangHocDev.Dolas_Pharmarcy.dto.response;

import com.hoangHocDev.Dolas_Pharmarcy.entity.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailResponse {
    String fullName;
    LocalDate dob;
    boolean verificationStatus;
    Gender gender;
}
