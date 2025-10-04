package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.*;
import com.hoanghocdev.dolaspharmacy.dto.response.*;
import com.hoanghocdev.dolaspharmacy.entity.Address;
import com.hoanghocdev.dolaspharmacy.entity.Order;
import com.hoanghocdev.dolaspharmacy.entity.Order_;
import com.hoanghocdev.dolaspharmacy.entity.UserEntity_;
import com.hoanghocdev.dolaspharmacy.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User")
@Validated
public class UserEntityController {
    UserEntityService userEntityService;
    OrderService orderService;
    AddressService addressService;
    FavouriteService favouriteService;

    @GetMapping
    public ApiResponse<Page<UserResponse>> findAll(@PageableDefault(page = 0, size = 16,
                                                        sort = UserEntity_.USERNAME,
                                                        direction = Sort.Direction.ASC)
                                                       Pageable pageable) {
        return ApiResponse.<Page<UserResponse>>builder()
                .result(userEntityService.findAll(pageable))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userEntityService.createUser(request))
                .build();
    }

    @PostMapping("/search")
    public ApiResponse<Page<UserResponse>> createUser(@RequestBody @Valid UserEntitySearchRequest request, @PageableDefault(page = 0, size = 16,
            sort = UserEntity_.USERNAME,
            direction = Sort.Direction.ASC)
    Pageable pageable) {
        return ApiResponse.<Page<UserResponse>>builder()
                .result(userEntityService.search(request, pageable))
                .build();
    }

    @PutMapping
    public ApiResponse<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userEntityService.updateUser(request))
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<Object> deleteUser(@PathVariable String userId) {
        userEntityService.delete(userId);
        return ApiResponse.builder()
                .build();
    }


    //get your detail
    @GetMapping("/me")
    public ApiResponse<UserResponse> findMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userEntityService.findMyInfo())
                .build();
    }

    //get your orders
    @GetMapping("/me/orders")
    public ApiResponse<Page<OrderResponse>> findMyOrders(Pageable pageable) {
        return ApiResponse.<Page<OrderResponse>>builder()
                .result(orderService.findMyOrders(pageable))
                .build();
    }

    //get your addresses
    @GetMapping("/me/addresses")
    public ApiResponse<Page<AddressResponse>> findMyAddresses(Pageable pageable) {
        return ApiResponse.<Page<AddressResponse>>builder()
                .result(addressService.findMyAddresses(pageable))
                .build();
    }

    @PostMapping("/me/addresses")
    public ApiResponse<AddressResponse> findMyAddresses(@RequestBody AddressRequest request) {
        return ApiResponse.<AddressResponse>builder()
                .result(addressService.create(request))
                .build();
    }

    @PutMapping("/me/addresses/{addressId}")
    public ApiResponse<AddressResponse> updateAddress(@PathVariable String addressId, @RequestBody AddressRequest request) {
        return ApiResponse.<AddressResponse>builder()
                .result(addressService.update(addressId, request))
                .build();
    }

    @DeleteMapping("/me/addresses/{addressId}")
    public ApiResponse findMyAddresses(@PathVariable String addressId) {
        addressService.delete(addressId);
        return ApiResponse.<Page<AddressResponse>>builder()
                .build();
    }

    //Checkout
    @PostMapping("/me/checkout")
    public ApiResponse<OrderResponse> createOrderFromCart(@RequestBody @Valid OrderCreationRequest request) throws IOException {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

    //favourites
    @GetMapping("/me/favourites")
    public ApiResponse<FavouriteResponse> findFavourites(){
        return ApiResponse.<FavouriteResponse>builder()
                .result(favouriteService.findFavouriteList())
                .build();
    }

    @PostMapping("/me/favourites")
    public ApiResponse<FavouriteResponse> toggleFavourite(@RequestBody @Valid FavouriteRequest request){
        return ApiResponse.<FavouriteResponse>builder()
                .result(favouriteService.toggleFavourite(request.getProductId()))
                .build();
    }
}
