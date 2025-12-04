package com.ptit.librarymanagement.common.validation.customvalidation.datevaildation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;

public class BirthDateValidator implements ConstraintValidator<BirthDateValid, Date>
{

    private BirthDateValid birthDateValid;

    @Override
    public void initialize(BirthDateValid constraintAnnotation) {
        birthDateValid = constraintAnnotation;
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    public boolean isValid(Date date, ConstraintValidatorContext c) {
        int age = birthDateValid.age();

        if (date == null) return false;

        LocalDate birth = date.toLocalDate(); // ✔ java.sql.Date hỗ trợ trực tiếp
        int actualAge = Period.between(birth, LocalDate.now()).getYears();

        return actualAge >= age;
    }

}
