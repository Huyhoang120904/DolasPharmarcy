package com.hoanghocdev.dolaspharmacy.entity;

import com.hoanghocdev.dolaspharmacy.entity.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uni_slug", columnNames = "slug")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @NotNull
    @Column(name = "name")
    String productName;
    String sku;
    String origin;
    String warning;
    String ingredients;
    String dosage;
    String description;
    String usageInstruction;

    @NotNull
    String slug;

    boolean requiresPrescription;

    @Enumerated(EnumType.STRING)
    ProductStatus productStatus;

    @OneToMany(cascade = CascadeType.ALL)
    List<Image> images;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "target_id")
    Target target;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Variant> variants;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    Brand brand;

    @ManyToOne
    @JoinColumn(name = "catergory_id")
    Category category;

    @OneToOne
    Promotion promotion;
}
