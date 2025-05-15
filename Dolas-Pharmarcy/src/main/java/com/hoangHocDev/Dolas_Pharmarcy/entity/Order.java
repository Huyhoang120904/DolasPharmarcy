package com.hoangHocDev.Dolas_Pharmarcy.entity;

import com.hoangHocDev.Dolas_Pharmarcy.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String fullName;
    LocalDate receiveDate;
    String receiveTime;
    double total;
    double tax;
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Embedded
    Address address;

    @ManyToOne
    @JoinColumn(name = "user_detail_id")
    UserDetail userDetail;

    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems;

    @OneToOne
    Promotion promotion;
}
