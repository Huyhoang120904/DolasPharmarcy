package com.hoanghocdev.dolaspharmacy.entity;


import com.hoanghocdev.dolaspharmacy.entity.enums.PromotionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String promotionName;
    String code;
    LocalDate startDate;
    LocalDate endDate;
    double discountAmount;

    @Enumerated(EnumType.STRING)
    PromotionType promotionType;
}
