package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.CategoryRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.CategoryResponse;
import com.hoanghocdev.dolaspharmacy.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Tag(name = "Category")
@Validated
public class CategoryController {
    CategoryService categoryService;

    @GetMapping
    public ApiResponse<Page<CategoryResponse>> getCategoryByPage(@PageableDefault(page = 0, size = 16,
                                                                        sort = "productName",
                                                                        direction = Sort.Direction.ASC)
                                                                        Pageable pageable) {
        return ApiResponse.<Page<CategoryResponse>>builder()
                .result(categoryService.findByPage(pageable))
                .build();
    }

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
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
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable String categoryId,
                                                        @RequestBody @Valid CategoryRequest categoryRequest) {
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
