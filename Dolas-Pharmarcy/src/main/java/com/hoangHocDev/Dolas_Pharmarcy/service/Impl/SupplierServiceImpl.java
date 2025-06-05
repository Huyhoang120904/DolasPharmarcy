package com.hoangHocDev.Dolas_Pharmarcy.service.Impl;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.SupplierRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.SupplierResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Supplier;
import com.hoangHocDev.Dolas_Pharmarcy.exception.AppException;
import com.hoangHocDev.Dolas_Pharmarcy.exception.ErrorCode;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.SupplierMapper;
import com.hoangHocDev.Dolas_Pharmarcy.repository.SupplierRepository;
import com.hoangHocDev.Dolas_Pharmarcy.service.SupplierService;
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
    public List<SupplierResponse> findAll() {
        return List.of();
    }

//    public List<SupplierResponse> findAll() {
//        List<Supplier> suppliers = supplierRepository.findAll();
//        return suppliers.stream()
//                .map(supplierMapper::toSupplierResponse)
//                .collect(Collectors.toList());
//    }

    @Override
    public Page<SupplierResponse> findByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
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