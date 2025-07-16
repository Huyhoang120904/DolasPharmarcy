package com.hoanghocdev.dolaspharmacy.service;

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
import com.hoanghocdev.dolaspharmacy.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Address Service Unit Test")
class AddressServiceTest {

    AddressRequest request;

    Address address;

    AddressResponse response;

    @MockitoBean
    AddressRepository addressRepository;

    @MockitoBean
    UserDetailRepository userDetailRepository;

    @MockitoBean
    UserEntityService userEntityService;

    @MockitoBean
    AddressMapper addressMapper;

    @Autowired
    AddressServiceImpl addressService;

    @BeforeEach
    void setUp(){
        request  = AddressRequest.builder()
                .name("main office")
                .address("413 ấp Vĩnh Bình")
                .district("Châu Thị Kim")
                .primary(false)
                .phoneNumber("0318928301")
                .zipcode("12312")
                .province("Nohting")
                .ward("testing")
                .build();
        address = Address.builder()
                .name("main office")
                .address("413 ấp Vĩnh Bình")
                .district("Châu Thị Kim")
                .primary(false)
                .phoneNumber("0318928301")
                .zipcode("12312")
                .province("Nohting")
                .ward("testing")
                .build();

        address = Address.builder()
                .name("updated")
                .address("updated")
                .district("updated")
                .primary(true)
                .phoneNumber("updated")
                .zipcode("updated")
                .province("updated")
                .ward("updated")
                .build();

        response = AddressResponse.builder()
                .name("main office")
                .address("413 ấp Vĩnh Bình")
                .district("Châu Thị Kim")
                .primary(false)
                .phoneNumber("0318928301")
                .zipcode("12312")
                .province("Nohting")
                .ward("testing")
                .build();
    }

    @Nested
    @DisplayName("DELETE")
    class delete {
        @Test
        void testDeleteAddressNotExist(){
            when(addressRepository.existsById(anyString())).thenReturn(false);
            AppException ex =  assertThrows(AppException.class, () -> addressService.delete("test"));
            assertEquals(ex.getErrorCode().getCode(), ErrorCode.DATA_NOT_FOUND.getCode());
        }

        @Test
        void testDeleteSuccess(){
            String id = UUID.randomUUID().toString();
            when(addressRepository.existsById(id)).thenReturn(true);
            addressService.delete(id);
            verify(addressRepository).deleteById(id);
        }
    }

    @Nested
    @DisplayName("CREATE")
    class create {
        @Test
        void testAddAddressSucess(){
            when(userDetailRepository.findByUsername(anyString()))
                    .thenReturn(Optional.of(UserDetail.builder().fullName("blah").build()));
            when(userEntityService.findMyInfo())
                    .thenReturn(UserResponse.builder().username("test").build());
            when(addressMapper.toAddress(request))
                    .thenReturn(address);
            when(addressMapper.toResponse(address))
                    .thenReturn(response);
            when(addressRepository.save(addressMapper.toAddress(request)))
                    .thenReturn(address);
            AddressResponse result = addressService.create(request);
            assertEquals(result.getName(),"main office" );
            verify(addressRepository).save(addressMapper.toAddress(request));
        }

        @Test
        void testAddAddressUserNotFound(){
            when(userDetailRepository.findByUsername(anyString()))
                    .thenThrow(new AppException(ErrorCode.DATA_NOT_FOUND));
            when(userEntityService.findMyInfo())
                    .thenReturn(UserResponse.builder().username("test").build());
            when(addressMapper.toAddress(request))
                    .thenReturn(address);
            when(addressMapper.toResponse(address))
                    .thenReturn(response);
            when(addressRepository.save(addressMapper.toAddress(request)))
                    .thenReturn(address);
            AppException ex = assertThrows(AppException.class, () -> addressService.create(request));
            assertEquals(ex.getErrorCode().getCode(), ErrorCode.DATA_NOT_FOUND.getCode());
        }

    }

    @Nested
    @DisplayName("UDPATE")
    class update{

        @Test
        void testUpdateSuccess() {
            when(addressRepository.findById(anyString())).thenReturn(Optional.of(address));
            doNothing().when(addressMapper).updateAddress(request, address);

        }


    }
}