package com.hoanghocdev.dolaspharmacy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    double quantity;
    double finalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "variant_id")
    Variant variant;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    Order order;

    public OrderItem calculateFinalPrice() {
        if (this.variant.getProduct().getPromotion() != null &&
                this.variant.getProduct().getPromotion().getDiscountAmount() > 0) {
            this.finalPrice = this.quantity
                    * this.variant.getPrice()
                    * (1 - this.variant.getProduct().getPromotion().getDiscountAmount() / 100);
        } else {
            this.finalPrice = this.quantity * this.variant.getPrice();
        }
        return this;
    }

}
