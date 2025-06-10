package com.hoanghocdev.dolaspharmacy.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    double price;
    int stock;
    String unit;

    Boolean isPrimary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

}
