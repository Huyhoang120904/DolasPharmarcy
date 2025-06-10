package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.BrandRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.BrandResponse;
import com.hoanghocdev.dolaspharmacy.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Brand")
public class BrandController {
    BrandService brandService;

    @GetMapping
    public ApiResponse<Page<BrandResponse>> getBrandsByPage(@PageableDefault(page = 0, size = 100,
                                                            sort = "brandName",
                                                            direction = Sort.Direction.ASC)
                                                            Pageable pageable) {
        return ApiResponse.<Page<BrandResponse>>builder()
                .result(brandService.findByPage(pageable))
                .build();
    }

    @PostMapping
    public ApiResponse<BrandResponse> createBrand(@RequestBody BrandRequest request) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.create(request))
                .build();
    }

    @DeleteMapping("/{brandId}")
    public ApiResponse<Void> deleteBrand(@PathVariable String brandId) {
        brandService.delete(brandId);
        return ApiResponse.<Void>builder()
                .build();
    }


}
