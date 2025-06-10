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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Set;

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
    public ApiResponse<Page<ProductResponse>> getProductByPage(@PageableDefault(page = 0, size = 16,
                                                                                sort = "productName",
                                                                                direction = Sort.Direction.ASC)
                                                                                Pageable pageable) {
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.findProductByPage(pageable))
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
                                                            @PageableDefault(page = 0, size = 16,
                                                                    sort = "productName",
                                                                    direction = Sort.Direction.ASC)
                                                                    Pageable pageable) {
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.findAll(request, pageable))
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
    //with image

    //Note: On the frontend, you'll use "data:image/jpeg;base64," + base64String to display images.
    @GetMapping("/{productId}/images")
    public ApiResponse<List<String>> getProductImageById(@PathVariable String productId) {
        List<byte[]> images = productService.findImageByProductId(productId);
        List<String> base64Images = images.stream()
                .map(Base64.getEncoder()::encodeToString)
                .toList();

        return ApiResponse.<List<String>>builder()
                .result(base64Images)
                .build();
    }

    @PostMapping("/image")
    public ApiResponse<ProductResponse> createProductWithProduct(@RequestPart @Valid ProductCreationRequest request,
                                                                 @RequestPart Set<MultipartFile> imageFile) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.addNewProductWithImage(request, imageFile))
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
