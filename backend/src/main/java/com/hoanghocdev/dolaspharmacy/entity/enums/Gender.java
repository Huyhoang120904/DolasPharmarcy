package com.hoanghocdev.dolaspharmacy.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("M", "Nam"),
    FEMALE("F", "Nữ"),
    OTHERS("O", "Khác"),
            ;

    private String code;
    private String name;

    Gender(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
