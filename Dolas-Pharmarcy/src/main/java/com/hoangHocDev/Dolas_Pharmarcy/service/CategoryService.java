package com.hoangHocDev.Dolas_Pharmarcy.service;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.CategoryRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.CategoryResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService extends BaseCRUDService<CategoryResponse, CategoryRequest>{
    CategoryResponse findBySlug(String slug);
}
