package com.hoangHocDev.Dolas_Pharmarcy.dto.response;

import com.hoangHocDev.Dolas_Pharmarcy.entity.Product;
import com.hoangHocDev.Dolas_Pharmarcy.entity.UserDetail;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FavouriteResponse {
    @OneToOne
    UserDetail userDetail;

    @OneToMany()
    List<Product> products;
}
