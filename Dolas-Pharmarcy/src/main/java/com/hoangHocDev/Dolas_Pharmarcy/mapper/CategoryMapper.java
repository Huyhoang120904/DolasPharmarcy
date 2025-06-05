package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.CategoryRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.CategoryResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCatergory(CategoryRequest request);
    CategoryResponse toCatergoryResponse(Category category);

    void updateCatergory(CategoryRequest request, @MappingTarget Category category);
}
