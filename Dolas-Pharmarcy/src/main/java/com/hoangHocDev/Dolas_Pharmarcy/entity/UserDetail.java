package com.hoangHocDev.Dolas_Pharmarcy.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

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


    String fullName;
    LocalDate dob;
    boolean verificationStatus;

    @Embedded
    Address address;

    @OneToMany(fetch = FetchType.EAGER)
    List<Order> orders;
}
