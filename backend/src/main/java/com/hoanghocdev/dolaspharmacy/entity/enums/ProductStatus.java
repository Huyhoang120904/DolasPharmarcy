package com.hoanghocdev.dolaspharmacy.entity.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum ProductStatus {
    ACTIVE("A", "Hoạt động"),
    INACTIVE("IA", "Không hoạt động"),
    OUT_OF_STOCK("OOS", "Hết hàng"),
    OUT_OF_BUSSINESS("OOB" , "Ngừng kinh doanh")
    ;

    private String code;
    private String name;

    ProductStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
