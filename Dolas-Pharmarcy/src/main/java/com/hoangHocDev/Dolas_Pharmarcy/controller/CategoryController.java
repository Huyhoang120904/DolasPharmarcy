package com.hoangHocDev.Dolas_Pharmarcy.controller;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.CategoryRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.CategoryResponse;
import com.hoangHocDev.Dolas_Pharmarcy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @GetMapping
    public ApiResponse<Page<CategoryResponse>> getCategoryByPage(@RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<CategoryResponse>>builder()
                .result(categoryService.findByPage(page, size))
                .build();
    }

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.create(request))
                .build();
    }

    @GetMapping("/{categorySlug}")
    public ApiResponse<CategoryResponse> getCategoryBySlug(@PathVariable String categorySlug) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.findBySlug(categorySlug))
                .build();
    }

    @PutMapping("/{categoryId}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable String categoryId
            , @RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.update(categoryId, categoryRequest))
                .build();
    }


    @DeleteMapping("/{categoryId}")
    public ApiResponse deleteCategory(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
        return ApiResponse.builder()
                .build();
    }

}
