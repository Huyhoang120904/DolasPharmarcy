package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.SupplierRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.SupplierResponse;
import com.hoanghocdev.dolaspharmacy.entity.Supplier;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.SupplierMapper;
import com.hoanghocdev.dolaspharmacy.repository.SupplierRepository;
import com.hoanghocdev.dolaspharmacy.service.SupplierService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierServiceImpl implements SupplierService {

    SupplierRepository supplierRepository;
    SupplierMapper supplierMapper;

    @Override
    public void delete(String id) {
        if (!supplierRepository.existsById(id)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        supplierRepository.deleteById(id);
    }

    @Override
    public SupplierResponse create(SupplierRequest request) {
        Supplier supplier = supplierMapper.toSupplier(request);
        supplier = supplierRepository.save(supplier);
        return supplierMapper.toSupplierResponse(supplier);
    }

    @Override
    public Page<SupplierResponse> findByPage(Pageable pageable) {
        return supplierRepository.findAll(pageable)
                .map(supplierMapper::toSupplierResponse);
    }

    @Override
    public SupplierResponse update(String id, SupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        supplierMapper.updateSupplier(request, supplier);
        supplier = supplierRepository.save(supplier);
        return supplierMapper.toSupplierResponse(supplier);
    }

    @Override
    public SupplierResponse findByID(String id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return supplierMapper.toSupplierResponse(supplier);
    }
}