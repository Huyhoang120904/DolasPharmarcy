package com.hoangHocDev.Dolas_Pharmarcy.entity;


import com.hoangHocDev.Dolas_Pharmarcy.entity.enums.PromotionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

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
