package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ProductCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ProductUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ProductResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {
        ImageMapper.class,
        PromotionMapper.class,
        CategoryMapper.class,
        SupplierMapper.class,
        VariantMapper.class
})
public interface ProductMapper {
    Product toProduct(ProductUpdateRequest request);
    Product toProduct(ProductCreationRequest request);

    ProductResponse toResponse(Product product);

    void updateProduct(ProductUpdateRequest request, @MappingTarget Product product);
}
