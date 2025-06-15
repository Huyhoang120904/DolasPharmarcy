package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.ProductCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.ProductSearchRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.ProductUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.VariantRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ProductResponse;
import com.hoanghocdev.dolaspharmacy.entity.*;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.ImageMapper;
import com.hoanghocdev.dolaspharmacy.mapper.ProductMapper;
import com.hoanghocdev.dolaspharmacy.mapper.VariantMapper;
import com.hoanghocdev.dolaspharmacy.repository.*;
import com.hoanghocdev.dolaspharmacy.service.ProductService;
import com.hoanghocdev.dolaspharmacy.service.specifications.ProductSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

        Promotion promotion = promotionRepository.findById(creationRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Supplier supplier = supplierRepository.findById(creationRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Variant variant = variantRepository.findById(creationRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

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
        product.getVariants().add(variant);
        product.setPromotion(promotion);

        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional(rollbackOn = IOException.class)
    public ProductResponse addNewProductWithImage(ProductCreationRequest creationRequest, Set<MultipartFile> imageFiles) {
        Product product = productMapper.toProduct(creationRequest);

        Category category = categoryRepository.findById(creationRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Promotion promotion = promotionRepository.findById(creationRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Supplier supplier = supplierRepository.findById(creationRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        List<Variant> variants = creationRequest.getVariants().stream()
                .map(variantMapper::toVariant).toList();

        if (variants.stream().filter(Variant::getIsPrimary).count() != 1) {
            throw new AppException(ErrorCode.INVALID_VARIANTS);
        }

        List<Image> images = new ArrayList<>();

        imageFiles.forEach(imageFile -> {
            try {
                images.add(Image.builder()
                        .alt(imageFile.getOriginalFilename())
                        .imageData(imageFile.getBytes())
                        .build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

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
        product.setPromotion(promotion);

        product.setVariants(variants);
        updatePrimaryVariantPrice(product);

        product.setImages(images);

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

        Promotion promotion = promotionRepository.findById(updateRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Supplier supplier = supplierRepository.findById(updateRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        List<Variant> variants = updateRequest.getVariants().stream()
                .map(variantMapper::toVariant).toList();

        if (variants.stream().filter(Variant::getIsPrimary).count() != 1) {
            throw new AppException(ErrorCode.INVALID_VARIANTS);
        }

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
        product.setPromotion(promotion);
        product.setVariants(variants);

        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Override
    public void deleteProduct(String id) {
        if(!productRepository.existsById(id)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        promotionRepository.deleteById(id);
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

    @Override
    public List<byte[]> findImageByProductId(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return product.getImages().stream()
                .map(Image::getImageData)
                .toList();
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
