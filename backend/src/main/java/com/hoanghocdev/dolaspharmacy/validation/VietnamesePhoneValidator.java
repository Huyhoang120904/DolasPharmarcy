package com.hoanghocdev.dolaspharmacy.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VietnamesePhoneValidator implements ConstraintValidator<ValidVietnamesePhone, String> {
    private static final String VIETNAMESE_PHONE_PATTERN = 
        "^(\\+84|0)(3[2-9]|5[689]|7[06-9]|8[1-5]|9\\d)\\d{7}$";

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return phoneNumber == null || phoneNumber.matches(VIETNAMESE_PHONE_PATTERN);
    }
}
