package com.hoangHocDev.Dolas_Pharmarcy.controller;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.SupplierRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.SupplierResponse;
import com.hoangHocDev.Dolas_Pharmarcy.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class SupplierController {
    SupplierService supplierService;

    @GetMapping
    public ApiResponse<Page<SupplierResponse>> getSupplierByPage(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size){
        return ApiResponse.<Page<SupplierResponse>>builder()
                .result(supplierService.findByPage(page, size))
                .build();
    }

    @GetMapping("/{supplierId}")
    public ApiResponse<SupplierResponse> getSupplierById(@PathVariable String supplierId){
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.findByID(supplierId))
                .build();
    }

    @PostMapping
    public ApiResponse<SupplierResponse> createSupplier(@RequestBody SupplierRequest request){
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.create(request))
                .build();
    }

    @PutMapping("/{supplierId}")
    public ApiResponse<SupplierResponse> updateSupplier(@PathVariable String supplierId,@RequestBody SupplierRequest request){
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.update(supplierId,request))
                .build();
    }

    @DeleteMapping("/{supplierId}")
    public ApiResponse deleteSupplier(@PathVariable String supplierId){
        supplierService.delete(supplierId);
        return ApiResponse.builder()
                .build();
    }


}
