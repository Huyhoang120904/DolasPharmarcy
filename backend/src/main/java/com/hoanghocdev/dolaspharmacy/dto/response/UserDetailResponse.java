package com.hoanghocdev.dolaspharmacy.dto.response;

import com.hoanghocdev.dolaspharmacy.entity.Address;
import com.hoanghocdev.dolaspharmacy.entity.enums.Gender;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailResponse {
    String id;
    String fullName;
    String email;
    LocalDate dob;
    boolean verificationStatus;
    Gender gender;
    Set<AddressResponse> addresses;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
