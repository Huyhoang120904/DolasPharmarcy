package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.SupplierRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.SupplierResponse;
import org.springframework.stereotype.Service;

@Service
public interface SupplierService extends BaseCRUDService<SupplierResponse, SupplierRequest> {
}
