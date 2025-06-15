package com.hoanghocdev.dolaspharmacy.dto.request;

import com.hoanghocdev.dolaspharmacy.entity.Address;
import com.hoanghocdev.dolaspharmacy.entity.Order;
import com.hoanghocdev.dolaspharmacy.entity.UserEntity;
import com.hoanghocdev.dolaspharmacy.entity.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailRequest {

    @NotBlank(message = "Email must not be blank")
    @Size(max = 200, message = "Email must not exceed 200 characters")
    String email;

    @NotBlank(message = "Full name must not be blank")
    @Size(max = 200, message = "Full name must not exceed 200 characters")
    String fullName;

    LocalDate dob;

    @NotBlank(message = "Gender must not be blank")
    @Size(max = 200, message = "Gender must not exceed 200 characters")
    String gender;
}
