package com.hoanghocdev.dolaspharmacy.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String promotionName;
    String code;
    LocalDate startDate;
    LocalDate endDate;
    double discountAmount;
    String slug;


    @OneToOne
    Image image;

    @Enumerated(EnumType.STRING)
    PromotionType promotionType;
}
