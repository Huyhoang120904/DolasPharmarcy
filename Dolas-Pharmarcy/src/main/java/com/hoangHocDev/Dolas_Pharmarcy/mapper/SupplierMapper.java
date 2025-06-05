package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.SupplierRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.SupplierResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toSupplier(SupplierRequest request);
    SupplierResponse toSupplierResponse(Supplier supplier);


    void updateSupplier(SupplierRequest request,@MappingTarget Supplier supplier);
}
