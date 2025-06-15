package com.hoanghocdev.dolaspharmacy.entity;

import com.hoanghocdev.dolaspharmacy.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne
    UserEntity userEntity;

    String email;
    String fullName;
    LocalDate dob;

    boolean verificationStatus;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @OneToMany(mappedBy = "userDetail")
    Set<Address> addresses;

    @OneToMany(fetch = FetchType.EAGER)
    List<Order> orders;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime updatedAt;
}
