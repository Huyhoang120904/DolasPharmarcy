package com.hoangHocDev.Dolas_Pharmarcy.service;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ProductCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ProductUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.VariantRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ProductResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.VariantResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Variant;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductResponse getProductBySlug(String slug);
    Page<ProductResponse> getProductByPage(int limit, int page);
    ProductResponse updateProduct(String productId, ProductUpdateRequest updateRequest);
    ProductResponse addNewProduct(ProductCreationRequest updateRequest);
    void deleteProduct(String id);
    ProductResponse addVariant(String productId,VariantRequest request);
    ProductResponse deleteVariant(String productId,String variantId);
//    List<VariantResponse> getVariant(String productId);
}
