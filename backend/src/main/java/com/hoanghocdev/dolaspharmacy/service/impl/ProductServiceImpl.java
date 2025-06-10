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

        Brand brand = null;

        if (brandRepository.findById(creationRequest.getBrandName()).isPresent()) {
            brand = brandRepository.findById(creationRequest.getBrandName()).get();
        } else {
            brand = Brand.builder()
                    .brandName(creationRequest.getBrandName())
                    .origin(creationRequest.getBrandOrigin())
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

        Brand brand = null;

        if (brandRepository.findById(creationRequest.getBrandName()).isPresent()) {
            brand = brandRepository.findById(creationRequest.getBrandName()).get();
        } else {
            brand = Brand.builder()
                    .brandName(creationRequest.getBrandName())
                    .origin(creationRequest.getBrandOrigin())
                    .build();
            brand = brandRepository.save(brand);
        }

        product.setBrand(brand);
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setPromotion(promotion);

        product.setVariants(variants);

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

        Brand brand = null;

        if (brandRepository.findById(updateRequest.getBrandName()).isPresent()) {
            brand = brandRepository.findById(updateRequest.getBrandName()).get();
        } else {
            brand = Brand.builder()
                    .brandName(updateRequest.getBrandName())
                    .origin(updateRequest.getBrandOrigin())
                    .build();
            brand = brandRepository.save(brand);
        }
        product.setBrand(brand);

        product.setCategory(category);
        product.setSupplier(supplier);
        product.setPromotion(promotion);

        product = productRepository.save(product);
        return productMapper.toResponse(product);
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
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse deleteVariant(String productId, String variantId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        product.getVariants().remove(variant);

        product = productRepository.save(product);

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

}
