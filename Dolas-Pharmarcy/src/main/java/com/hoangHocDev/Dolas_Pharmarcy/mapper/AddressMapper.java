package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.AddressRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.AddressResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(AddressRequest request);
    AddressResponse toAddressReponse(Address address);

    void updateAddress(AddressRequest request, @MappingTarget Address address);
}
