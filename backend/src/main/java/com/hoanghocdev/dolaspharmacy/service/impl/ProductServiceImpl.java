package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.ProductCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.ProductSearchRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.ProductUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.VariantRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ProductResponse;
import com.hoanghocdev.dolaspharmacy.entity.*;
import com.hoanghocdev.dolaspharmacy.entity.enums.ProductStatus;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.ProductMapper;
import com.hoanghocdev.dolaspharmacy.mapper.VariantMapper;
import com.hoanghocdev.dolaspharmacy.repository.*;
import com.hoanghocdev.dolaspharmacy.service.ProductService;
import com.hoanghocdev.dolaspharmacy.service.specifications.ProductSpecification;
import com.hoanghocdev.dolaspharmacy.utils.SlugUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
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
    VariantRepository variantRepository;
    VariantMapper variantMapper;
    private final BrandRepository brandRepository;
    private final TargetRepository targetRepository;

    @Override
    public ProductResponse findProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return productMapper.toResponse(product);
    }

    @Override
    public Page<ProductResponse> findProductByPage(Pageable pageable) {
        Page<Product> pageProducts = productRepository.findAll(pageable);
        return pageProducts.map(productMapper::toResponse);
    }

    @Override
    @Transactional
    public ProductResponse addNewProduct(ProductCreationRequest creationRequest) {
        Product product = productMapper.toProduct(creationRequest);

        Category category = categoryRepository.findById(creationRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Supplier supplier = supplierRepository.findById(creationRequest.getSupplierId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

//        List<Variant> variants = creationRequest.getVariants().stream()
//                .map(variantMapper::toVariant).toList();
//
//        if (variants.stream().filter(Variant::getIsPrimary).count() != 1) {
//            throw new AppException(ErrorCode.INVALID_VARIANTS);
//        }

        Target target = null;

        if (targetRepository.findById(creationRequest.getTarget().getTargetName()).isPresent()) {
            target = targetRepository.findById(creationRequest.getTarget().getTargetName()).get();
        } else {
            target = Target.builder()
                    .targetName(creationRequest.getTarget().getTargetName())
                    .description(creationRequest.getTarget().getDescription())
                    .build();
            target = targetRepository.save(target);
        }
        product.setTarget(target);

        Brand brand = null;

        if (brandRepository.findById(creationRequest.getBrand().getBrandName()).isPresent()) {
            brand = brandRepository.findById(creationRequest.getBrand().getBrandName()).get();
        } else {
            brand = Brand.builder()
                    .brandName(creationRequest.getBrand().getBrandName())
                    .origin(creationRequest.getBrand().getBrandOrigin())
                    .build();
            brand = brandRepository.save(brand);
        }

        product.setBrand(brand);
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setVariants(null);
        product.setSlug(SlugUtils.toSlug(product.getProductName()));
        product = productRepository.save(product);

        List<Variant> variants = creationRequest.getVariants().stream().map(variantMapper::toVariant).toList();
        Product finalProduct = product;
        variants.forEach(variant -> variant.setProduct(finalProduct));

        variants = variantRepository.saveAll(variants);

        product.setVariants(variants);
        product = productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(String productId, ProductUpdateRequest updateRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        productMapper.updateProduct(updateRequest, product);

        Category category = categoryRepository.findById(updateRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));


        Supplier supplier = supplierRepository.findById(updateRequest.getSupplierId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Target target = null;

        if (targetRepository.findById(updateRequest.getTarget().getTargetName()).isPresent()) {
            target = targetRepository.findById(updateRequest.getTarget().getTargetName()).get();
        } else {
            target = Target.builder()
                    .targetName(updateRequest.getTarget().getTargetName())
                    .description(updateRequest.getTarget().getDescription())
                    .build();
            target = targetRepository.save(target);
        }
        product.setTarget(target);

        Brand brand = null;

        if (brandRepository.findById(updateRequest.getBrand().getBrandName()).isPresent()) {
            brand = brandRepository.findById(updateRequest.getBrand().getBrandName()).get();
        } else {
            brand = Brand.builder()
                    .brandName(updateRequest.getBrand().getBrandName())
                    .origin(updateRequest.getBrand().getBrandOrigin())
                    .build();
            brand = brandRepository.save(brand);
        }

        product.setBrand(brand);
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setSlug(SlugUtils.toSlug(product.getProductName()));
        product = productRepository.save(product);

        List<String> variantIds = product.getVariants().stream().map(Variant::getId).toList();

        List<Variant> variants = variantRepository.findAllById(variantIds);

        Product finalProduct = product;
        variants.forEach(variant -> variant.setProduct(finalProduct));
        variantRepository.saveAll(variants);

        return productMapper.toResponse(product);
    }

    @Override
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        product.setProductStatus(ProductStatus.INACTIVE);
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackOn = AppException.class)
    public ProductResponse addVariant(String productId, VariantRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        Variant variant = variantMapper.toVariant(request);
        variant.setProduct(product);

        if (variant.getIsPrimary())
            product.getVariants().stream().forEach(v -> v.setIsPrimary(false));

        product.getVariants().add(variant);

        if (product.getVariants().stream().filter(Variant::getIsPrimary).count() != 1) {
            throw new AppException(ErrorCode.INVALID_VARIANTS);
        }

        product = productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse deleteVariant(String productId, String variantId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        product.getVariants().remove(variant);
        variant.setProduct(null);

        if (product.getVariants().stream().filter(Variant::getIsPrimary).count() != 1) {
            throw new AppException(ErrorCode.PRIMARY_VARIANT_CANNOT_BE_DELETED);
        }

        variantRepository.save(variant);
        productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Override
    public Page<ProductResponse> findAll(ProductSearchRequest request, Pageable pageable) {
        return productRepository.findAll(ProductSpecification.getSpecification(request), pageable)
                .map(productMapper::toResponse);
    }

    public void updatePrimaryVariantPrice(Product product) {
        product.setPrimaryVariantPrice(
                product.getVariants().stream()
                        .filter(v -> Boolean.TRUE.equals(v.getIsPrimary()))
                        .map(Variant::getPrice)
                        .findFirst()
                        .orElse(null)
        );
        productRepository.save(product);
    }

}
