package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.AddressRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.AddressResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.UserResponse;
import com.hoanghocdev.dolaspharmacy.entity.Address;
import com.hoanghocdev.dolaspharmacy.entity.UserDetail;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.AddressMapper;
import com.hoanghocdev.dolaspharmacy.repository.AddressRepository;
import com.hoanghocdev.dolaspharmacy.repository.UserDetailRepository;
import com.hoanghocdev.dolaspharmacy.service.AddressService;
import com.hoanghocdev.dolaspharmacy.service.UserEntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    AddressRepository addressRepository;
    AddressMapper addressMapper;
    UserEntityService userEntityService;
    UserDetailRepository userDetailRepository;

    @Override
    public void delete(String id) {
        if (!addressRepository.existsById(id)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        addressRepository.deleteById(id);
    }

    @Override
    public AddressResponse create(AddressRequest addressRequest) {
        UserResponse user = userEntityService.findMyInfo();

        UserDetail userDetail = userDetailRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Address address = addressMapper.toAddress(addressRequest);

        address.setUserDetail(userDetail);
        address = addressRepository.save(address);
        return addressMapper.toResponse(address);
    }

    @Override
    public Page<AddressResponse> findByPage(Pageable pageable) {
        return addressRepository.findAll(pageable)
                .map(addressMapper::toResponse);
    }

    @Override
    public AddressResponse update(String id, AddressRequest addressRequest) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        addressMapper.updateAddress(addressRequest, address);
        address = addressRepository.save(address);
        return addressMapper.toResponse(address);
    }

    @Override
    public AddressResponse findByID(String id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        return addressMapper.toResponse(address);
    }


    @Override
    public Page<AddressResponse> findMyAddresses(Pageable pageable) {
        UserResponse user = userEntityService.findMyInfo();

        UserDetail userDetail = userDetailRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        return  addressRepository.findByUserDetail(userDetail, pageable)
                .map(addressMapper::toResponse);
    }
}
