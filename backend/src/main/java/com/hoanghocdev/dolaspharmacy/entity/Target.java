package com.hoanghocdev.dolaspharmacy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Target {
    @Id
    String targetName;

    String description;

    @OneToMany(mappedBy = "target",fetch = FetchType.LAZY)
    List<Product> products;
}
