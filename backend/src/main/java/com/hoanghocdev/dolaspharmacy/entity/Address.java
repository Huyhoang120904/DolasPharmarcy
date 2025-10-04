package com.hoanghocdev.dolaspharmacy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String address;
    String district;
    String ward;
    String province;
    String phoneNumber;

    String name;
    String zipcode;

    @Column(name = "is_primary", columnDefinition = "TINYINT(1)")
    Boolean primary;

    @ManyToOne
    @JoinColumn(name = "userDetail_id")
    @JsonBackReference
    UserDetail userDetail;

    @OneToMany(mappedBy = "address")
    Set<Order> orders;
}
