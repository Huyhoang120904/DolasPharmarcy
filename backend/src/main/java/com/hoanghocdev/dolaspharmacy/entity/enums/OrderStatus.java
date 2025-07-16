package com.hoanghocdev.dolaspharmacy.entity.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING("P", "Chờ xử lý"),
    PAID("P", "Đã thanh toán"),
    UNPAID("UD", "Chưa được thanh toán"),
    SHIPPING("S", "Đang giao hàng"),
    COMPLETED("CP", "Đã hoàn tất"),
    CANCELLED("CC", "Đã huỷ"),
            ;

    private String code;
    private String name;

    OrderStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
