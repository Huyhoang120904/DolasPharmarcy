package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.ProductCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.ProductUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ProductResponse;
import com.hoanghocdev.dolaspharmacy.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {
        ImageMapper.class,
        PromotionMapper.class,
        CategoryMapper.class,
        SupplierMapper.class,
        VariantMapper.class,
        BrandMapper.class,
        TargetMapper.class
})
public interface ProductMapper {

    Product toProduct(ProductUpdateRequest request);
    Product toProduct(ProductCreationRequest request);

    ProductResponse toResponse(Product product);

    void updateProduct(ProductUpdateRequest request, @MappingTarget Product product);
}
