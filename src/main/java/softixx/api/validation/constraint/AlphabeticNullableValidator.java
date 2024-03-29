package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidAlphabeticNullable;

public class AlphabeticNullableValidator implements ConstraintValidator<ValidAlphabeticNullable, String> {

    @Override
    public boolean isValid(String letter, ConstraintValidatorContext context) {   
        return (validateAlphabetic(letter));
    }
    
    private boolean validateAlphabetic(String letter) {
        return UValidator.validateNullableAlphabetic(letter);
    }
    
}