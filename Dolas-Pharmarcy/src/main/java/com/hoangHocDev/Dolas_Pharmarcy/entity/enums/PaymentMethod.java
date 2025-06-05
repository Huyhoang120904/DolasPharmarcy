package com.hoangHocDev.Dolas_Pharmarcy.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CASH_ON_DELIVERY("COD", "Trả khi ship đến"),
    CARD("C", "Thẻ"),
    E_BANKING("EB", "Ngân hàng điện tử"),
            ;

    private String code;
    private String name;

    PaymentMethod(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
