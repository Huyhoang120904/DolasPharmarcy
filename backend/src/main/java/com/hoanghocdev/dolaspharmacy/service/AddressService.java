package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.AddressRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.AddressResponse;
import com.hoanghocdev.dolaspharmacy.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AddressService extends BaseCRUDService<AddressResponse, AddressRequest>{
    Page<AddressResponse> findMyAddresses(Pageable pageable);
}
