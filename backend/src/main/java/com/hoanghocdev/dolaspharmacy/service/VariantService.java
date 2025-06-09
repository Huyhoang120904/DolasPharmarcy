package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.VariantRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.VariantResponse;
import org.springframework.stereotype.Service;

@Service
public interface VariantService extends BaseCRUDService<VariantResponse, VariantRequest>{
}
