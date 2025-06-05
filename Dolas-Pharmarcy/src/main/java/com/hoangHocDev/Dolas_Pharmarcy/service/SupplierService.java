package com.hoangHocDev.Dolas_Pharmarcy.service;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.SupplierRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.SupplierResponse;
import org.springframework.stereotype.Service;

@Service
public interface SupplierService extends BaseCRUDService<SupplierResponse, SupplierRequest> {
}
