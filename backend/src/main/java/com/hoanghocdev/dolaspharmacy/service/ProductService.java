package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.ProductCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.ProductSearchRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.ProductUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.VariantRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public interface ProductService {
    ProductResponse findProductBySlug(String slug);

    Page<ProductResponse> findProductByPage(Pageable pageable);

    ProductResponse updateProduct(String productId, ProductUpdateRequest request);

    ProductResponse addNewProduct(ProductCreationRequest request);

    ProductResponse addNewProductWithImage(ProductCreationRequest request, Set<MultipartFile> imageFile);

    void deleteProduct(String id);

    ProductResponse addVariant(String productId, VariantRequest request);

    ProductResponse deleteVariant(String productId, String variantId);

    Page<ProductResponse> findAll(ProductSearchRequest request, Pageable pageable);
    List<byte[]> findImageByProductId(String productId);
}
