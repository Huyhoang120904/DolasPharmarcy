package com.hoanghocdev.dolaspharmacy.dto.response;

import com.hoanghocdev.dolaspharmacy.entity.Product;
import com.hoanghocdev.dolaspharmacy.entity.UserDetail;
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
    String id;
    List<ProductResponse> products;
}
