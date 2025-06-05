package com.hoangHocDev.Dolas_Pharmarcy.service.Impl;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ProductCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ProductUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.VariantRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ProductResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.VariantResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Category;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Product;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Variant;
import com.hoangHocDev.Dolas_Pharmarcy.exception.AppException;
import com.hoangHocDev.Dolas_Pharmarcy.exception.ErrorCode;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.ImageMapper;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.ProductMapper;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.VariantMapper;
import com.hoangHocDev.Dolas_Pharmarcy.repository.*;
import com.hoangHocDev.Dolas_Pharmarcy.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class ProductServiceImpl implements ProductService {

    CategoryRepository categoryRepository;
    PromotionRepository promotionRepository;
   SupplierRepository supplierRepository;
    ProductRepository productRepository;
    ProductMapper productMapper;
    ImageRepository imageRepository;
    ImageMapper imageMapper;
    VariantRepository variantRepository;
    VariantMapper variantMapper;

    @Override
    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return productMapper.toReponse(product);
    }

    @Override
    public Page<ProductResponse> getProductByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> pageProducts = productRepository.findAll(pageable);
        return pageProducts.map(productMapper::toReponse);
    }

    @Override
    public ProductResponse addNewProduct(ProductCreationRequest creationRequest) {
        Product product = productMapper.toProduct(creationRequest);

        Category category = categoryRepository.findById(creationRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

//        Promotion promotion = promotionRepository.findById(creationRequest.getCategoryId())
//                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
//
//        Supplier supplier= supplierRepository.findById(creationRequest.getCategoryId())
//                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
//
//        Variant variant = variantRepository.findById(creationRequest.getCategoryId())
//                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        product.setCategory(category);
//        product.setSupplier(supplier);
//        product.getVariants().add(variant);
//        product.setPromotion(promotion);

        product = productRepository.save(product);
        return productMapper.toReponse(product);
    }

    @Override
    public ProductResponse updateProduct(String productId, ProductUpdateRequest updateRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        productMapper.updateProduct(updateRequest, product);

        Category category = categoryRepository.findById(updateRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

//        Promotion promotion = promotionRepository.findById(updateRequest.getCategoryId())
//                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
//
//        Supplier supplier = supplierRepository.findById(updateRequest.getCategoryId())
//                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        product.setCategory(category);
//        product.setSupplier(supplier);
//        product.setPromotion(promotion);

        product = productRepository.save(product);
        return productMapper.toReponse(product);
    }

    @Override
    public void deleteProduct(String id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public ProductResponse addVariant(String productId, VariantRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        Variant variant = variantMapper.toVariant(request);
        variant.setProduct(product);
        product.getVariants().add(variant);
        product = productRepository.save(product);
        return productMapper.toReponse(product);
    }

    @Override
    public ProductResponse deleteVariant(String productId, String variantId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        product.getVariants().remove(variant);

        product = productRepository.save(product);

        return productMapper.toReponse(product);
    }

//    @Override
//    public List<VariantResponse> getVariant(String productId) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
//
//        return product.getVariants().stream()
//                .map(variantMapper::toReponse)
//                .toList();
//    }
}
