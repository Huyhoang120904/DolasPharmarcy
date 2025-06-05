package com.hoangHocDev.Dolas_Pharmarcy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne
    UserDetail userDetail;

    @OneToMany(cascade = CascadeType.ALL)
    List<OrderItem> orderItems;
}
