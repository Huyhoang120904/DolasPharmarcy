package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.BrandRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.BrandResponse;
import org.springframework.stereotype.Service;

@Service
public interface BrandService extends BaseCRUDService<BrandResponse, BrandRequest> {
}
