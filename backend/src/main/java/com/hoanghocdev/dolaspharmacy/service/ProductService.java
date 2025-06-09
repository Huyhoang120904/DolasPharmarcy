package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.ProductCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.ProductSearchRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.ProductUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.VariantRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    ProductResponse findProductBySlug(String slug);

    Page<ProductResponse> findProductByPage(int limit, int page);

    ProductResponse updateProduct(String productId, ProductUpdateRequest updateRequest);

    ProductResponse addNewProduct(ProductCreationRequest updateRequest);

    void deleteProduct(String id);

    ProductResponse addVariant(String productId, VariantRequest request);

    ProductResponse deleteVariant(String productId, String variantId);

    Page<ProductResponse> findAll(ProductSearchRequest request, int page, int size);
}
