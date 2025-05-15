package com.hoangHocDev.Dolas_Pharmarcy.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    String code;
    String contactName;
    String email;
    String phone;
    String website;
    String active;
    String description;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "supplier")
    List<Product> products;
}
