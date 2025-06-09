package com.hoanghocdev.dolaspharmacy.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {
    private String startDateField;
    private String endDateField;

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.startDateField = constraintAnnotation.startDateField();
        this.endDateField = constraintAnnotation.endDateField();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            Field startField = obj.getClass().getDeclaredField(startDateField);
            Field endField = obj.getClass().getDeclaredField(endDateField);
            
            LocalDate startDate = (LocalDate) startField.get(obj);
            LocalDate endDate = (LocalDate) endField.get(obj);
            
            if (startDate == null || endDate == null) {
                return true; // Let other validators handle null checks
            }
            
            return endDate.isAfter(startDate);
        } catch (Exception e) {
            return false;
        }
    }
}
