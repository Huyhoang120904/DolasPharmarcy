package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.SupplierRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.SupplierResponse;
import com.hoanghocdev.dolaspharmacy.service.SupplierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Tag(name = "Supplier")
@Validated
public class SupplierController {
    SupplierService supplierService;

    @GetMapping
    public ApiResponse<Page<SupplierResponse>> getSupplierByPage(@PageableDefault(page = 0, size = 16,
                                                                    sort = "supplierName",
                                                                    direction = Sort.Direction.ASC)
                                                                     Pageable pageable){
        return ApiResponse.<Page<SupplierResponse>>builder()
                .result(supplierService.findByPage(pageable))
                .build();
    }

    @GetMapping("/{supplierId}")
    public ApiResponse<SupplierResponse> getSupplierById(@PathVariable String supplierId){
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.findByID(supplierId))
                .build();
    }

    @PostMapping
    public ApiResponse<SupplierResponse> createSupplier(@RequestBody @Valid SupplierRequest request){
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.create(request))
                .build();
    }

    @PutMapping("/{supplierId}")
    public ApiResponse<SupplierResponse> updateSupplier(@PathVariable String supplierId,
                                                        @RequestBody @Valid SupplierRequest request){
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.update(supplierId,request))
                .build();
    }

    @DeleteMapping("/{supplierId}")
    public ApiResponse<Object> deleteSupplier(@PathVariable String supplierId){
        supplierService.delete(supplierId);
        return ApiResponse.builder()
                .build();
    }


}
