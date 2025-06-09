package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.CategoryRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.CategoryResponse;
import com.hoanghocdev.dolaspharmacy.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCatergory(CategoryRequest request);
    CategoryResponse toCatergoryResponse(Category category);

    void updateCatergory(CategoryRequest request, @MappingTarget Category category);
}
