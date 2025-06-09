package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.SupplierRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.SupplierResponse;
import com.hoanghocdev.dolaspharmacy.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toSupplier(SupplierRequest request);
    SupplierResponse toSupplierResponse(Supplier supplier);


    void updateSupplier(SupplierRequest request,@MappingTarget Supplier supplier);
}
