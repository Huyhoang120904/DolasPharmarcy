package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.CategoryRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService extends BaseCRUDService<CategoryResponse, CategoryRequest>{
    CategoryResponse findBySlug(String slug);
}
