package com.hoanghocdev.dolaspharmacy.entity;

import com.hoanghocdev.dolaspharmacy.entity.enums.OrderStatus;
import com.hoanghocdev.dolaspharmacy.entity.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = true)
    Address address;

    @ManyToOne
    @JoinColumn(name = "user_detail_id")
    UserDetail userDetail;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItem> orderItems;

    @OneToOne
    Promotion promotion;

    @CreationTimestamp
    LocalDateTime createAt;

    @UpdateTimestamp
    LocalDateTime lastModifiedAt;

    public void calculateTotal(){
        this.total = 0;
        if (!CollectionUtils.isEmpty(this.orderItems)) {
            double tmp = 0;
            for (OrderItem orderItem : this.orderItems) {
                tmp+=orderItem.getFinalPrice();
            }
            this.total = tmp;
        }
    }

}
