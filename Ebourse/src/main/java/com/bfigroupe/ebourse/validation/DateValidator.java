package com.bfigroupe.ebourse.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String DATE_PATTERN = "([0-9]{4})-([0-9]{2})-([0-9]{2})";

    @Override
    public void initialize(final ValidDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String date, final ConstraintValidatorContext context) {
        return (validateDate(date));
    }

    private boolean validateDate(final String date) {
        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
