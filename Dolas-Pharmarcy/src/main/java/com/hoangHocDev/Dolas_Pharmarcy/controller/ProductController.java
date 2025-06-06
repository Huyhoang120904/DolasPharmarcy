package com.hoangHocDev.Dolas_Pharmarcy.controller;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ProductCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ProductSearchRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ProductUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.VariantRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ProductResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Product;
import com.hoangHocDev.Dolas_Pharmarcy.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class ProductController {
    ProductService productService;

    //product
    @GetMapping
    public ApiResponse<Page<ProductResponse>> getProductByPage(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.findProductByPage(page, size))
                .build();
    }

    @GetMapping("/{productSlug}")
    public ApiResponse<ProductResponse> getProductBySlug(@PathVariable String productSlug) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.findProductBySlug(productSlug))
                .build();
    }

    @PostMapping()
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductCreationRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.addNewProduct(request))
                .build();
    }

    @PostMapping("/search")
    public ApiResponse<Page<ProductResponse>> searchProduct(@RequestBody ProductSearchRequest request,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.findAll(request, page, size))
                .build();
    }

    @PutMapping("/{productId}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable String productId, @RequestBody ProductUpdateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(productId, request))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ApiResponse deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return ApiResponse.<ProductResponse>builder()
                .build();
    }

    //variant
    @PostMapping("/{productId}/variants")
    public ApiResponse<ProductResponse> createVariant(@PathVariable String productId, @RequestBody VariantRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.addVariant(productId, request))
                .build();
    }

    @DeleteMapping("/{productId}/delete/{variantId}")
    public ApiResponse<ProductResponse> deleteVariant(@PathVariable String productId, @PathVariable String variantId) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.deleteVariant(productId, variantId))
                .build();
    }

}
