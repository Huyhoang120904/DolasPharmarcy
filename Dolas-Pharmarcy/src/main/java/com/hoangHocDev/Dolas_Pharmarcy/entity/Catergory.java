package com.hoangHocDev.Dolas_Pharmarcy.entity;


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
public class Catergory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    String name;
    String description;
    boolean isActive;
    int totalProducts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    Catergory parentCatergory;

    @OneToMany(mappedBy = "parentCatergory")
    List<Catergory> catergoryChilrens;

    @OneToMany(mappedBy = "catergory")
    List<Product> products;

}
