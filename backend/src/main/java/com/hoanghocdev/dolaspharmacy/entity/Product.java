package com.hoanghocdev.dolaspharmacy.entity;

import com.hoanghocdev.dolaspharmacy.entity.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    Double primaryVariantPrice;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime lastModifiedAt;

    @Enumerated(EnumType.STRING)
    ProductStatus productStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    Promotion promotion;

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
        if (variants == null) {
            return;
        }
        this.setPrimaryVariantPrice(
                this.getVariants().stream()
                        .filter(v -> Boolean.TRUE.equals(v.getIsPrimary()))
                        .map(Variant::getPrice)
                        .findFirst()
                        .orElse(null)
        );
    }
}
