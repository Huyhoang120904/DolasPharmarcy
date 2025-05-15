package com.hoangHocDev.Dolas_Pharmarcy.entity;

import com.hoangHocDev.Dolas_Pharmarcy.entity.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    String sku;
    String origin;
    String warning;
    String ingredients;
    String dosage;
    String description;
    String usageInstruction;

    boolean requiresPrescription;

    @Enumerated(EnumType.STRING)
    ProductStatus productStatus;

    @OneToMany
    List<Image> images;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "target_id")
    Target target;

    @OneToMany(mappedBy = "product")
    List<Variant> variants;

    @ManyToOne()
    @JoinColumn(name = "catergory_id")
    Catergory catergory;

    @OneToOne
    Promotion promotion;
}
