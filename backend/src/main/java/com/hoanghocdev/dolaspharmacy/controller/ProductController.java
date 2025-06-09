package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.ProductCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.ProductSearchRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.ProductUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.VariantRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.ProductResponse;
import com.hoanghocdev.dolaspharmacy.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Product (Variant)")
@Validated
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
    public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreationRequest request) {
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
    public ApiResponse<ProductResponse> updateProduct(@PathVariable String productId,
                                                      @RequestBody @Valid ProductUpdateRequest request) {
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
    public ApiResponse<ProductResponse> createVariant(@PathVariable String productId,
                                                      @RequestBody @Valid VariantRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.addVariant(productId, request))
                .build();
    }

    @DeleteMapping("/{productId}/delete/{variantId}")
    public ApiResponse<ProductResponse> deleteVariant(@PathVariable String productId,
                                                      @PathVariable String variantId) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.deleteVariant(productId, variantId))
                .build();
    }

}
