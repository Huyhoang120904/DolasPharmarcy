package com.hoanghocdev.dolaspharmacy.entity.enums;

public enum PromotionType {
    FIXED_AMOUNT_PRODUCT("Khuyến mãi giá trị sản phẩm"),
    PERCENTAGE_PRODUCT("Khuyến mãi phần trăm sản phẩm"),
    FIXED_AMOUNT_ORDER("Khuyến mãi giá trị hoá đơn"),
    PERCENTAGE_ORDER("Khuyến mãi phần trăm hoá đơn");


    private String name;

    PromotionType(String s) {
        this.name = s;
    }
}
