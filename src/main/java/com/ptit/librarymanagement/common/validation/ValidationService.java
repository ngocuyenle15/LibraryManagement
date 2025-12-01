package com.ptit.librarymanagement.common.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;


import javax.swing.*;
import java.util.Set;


public class ValidationService {
    private final Validator validator;

    public ValidationService(Validator validator) {
        this.validator = validator;
    }

    public boolean checkConstraint (Object o) {
        Set<ConstraintViolation<Object>> violations = validator.validate(o);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Có lỗi xảy ra:\n");
            for (ConstraintViolation<Object> v : violations) {
                errorMessage.append("- ").append(v.getMessage()).append("\n");
            }
            JOptionPane.showMessageDialog(
                    null,
                    errorMessage.toString(),
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return true;
    }

    public boolean checkConstraint (Object o, Class<?> ... groups ) {
        Set<ConstraintViolation<Object>> violations = validator.validate(o, groups);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Có lỗi xảy ra:\n");
            for (ConstraintViolation<Object> v : violations) {
                errorMessage.append("- ").append(v.getMessage()).append("\n");
            }
            JOptionPane.showMessageDialog(
                    null,
                    errorMessage.toString(),
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return true;
    }

}
