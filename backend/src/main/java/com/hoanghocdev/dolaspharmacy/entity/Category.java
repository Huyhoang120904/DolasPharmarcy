package com.hoanghocdev.dolaspharmacy.entity;


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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "name")
    String categoryName;
    String description;
    String slug;
    boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    Image image;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    List<Category> categoryChildrens;

    @OneToMany(mappedBy = "category")
    List<Product> products;

}
