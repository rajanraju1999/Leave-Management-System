package com.lms.leavemanagementsystem.validation;

import com.lms.leavemanagementsystem.exception.CustomException.EnumValidationException;
import com.lms.leavemanagementsystem.validation.EnumValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, CharSequence> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValid annotation) {
        enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }

        for (Enum<?> enumValue : enumClass.getEnumConstants()) {
            if (enumValue.name().equals(value.toString())) {
                return true;
            }
        }

        throw new EnumValidationException(context.getDefaultConstraintMessageTemplate() + value);
    }
}
