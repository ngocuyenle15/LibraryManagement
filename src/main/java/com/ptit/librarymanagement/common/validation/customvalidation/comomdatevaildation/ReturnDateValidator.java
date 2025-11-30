package com.ptit.librarymanagement.common.validation.customvalidation.comomdatevaildation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.sql.Date;

public class ReturnDateValidator  implements ConstraintValidator<ReturnDateValid, Date> {
    @Override
    public boolean isValid(Date returnDate, ConstraintValidatorContext constraintValidatorContext) {
        if (returnDate != null && returnDate.compareTo(new java.sql.Date(new java.util.Date().getTime())) > 0) {
            return true;
        }
        return false;
    }
}
