package com.ptit.librarymanagement.common.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public final class ValidatorManager {
    private static final Validator validator;
    private ValidatorManager () {}
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    public static Validator getValidator () {
        return ValidatorManager.validator;
    }
}
