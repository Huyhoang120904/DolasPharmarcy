package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.BrandRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.BrandResponse;
import com.hoanghocdev.dolaspharmacy.entity.Brand;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.BrandMapper;
import com.hoanghocdev.dolaspharmacy.repository.BrandRepository;
import com.hoanghocdev.dolaspharmacy.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;

    @Override
    public void delete(String id) {
       if (!brandRepository.existsById(id)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
       }
        brandRepository.deleteById(id);
    }

    @Override
    public BrandResponse create(BrandRequest request) {
        Brand brand = brandMapper.toBrand(request);
        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.toResponse(savedBrand);
    }

    @Override
    public Page<BrandResponse> findByPage(Pageable pageable) {
        Page<Brand> brandPage = brandRepository.findAll(pageable);
        return brandPage.map(brandMapper::toResponse);
    }

    @Override
    public BrandResponse update(String id, BrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        brand.setBrandName(request.getBrandName());
        brand.setOrigin(request.getBrandOrigin());
        Brand updatedBrand = brandRepository.save(brand);
        return brandMapper.toResponse(updatedBrand);
    }

    @Override
    public BrandResponse findByID(String id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return brandMapper.toResponse(brand);
    }
}
