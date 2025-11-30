package com.ptit.librarymanagement.common.validation.customvalidation.comomdatevaildation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class DateValidator implements ConstraintValidator<DateValid, java.sql.Date> {
    private DateValid dateValidAnnotationProxy;

    @Override
    public void initialize(DateValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.dateValidAnnotationProxy = constraintAnnotation;
    }

    @Override
    public boolean isValid(java.sql.Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null)
            return false;
        return true;
    }
}
