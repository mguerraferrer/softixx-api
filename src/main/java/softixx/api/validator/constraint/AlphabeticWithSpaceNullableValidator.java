package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidAlphabeticWithSpaceNullable;

public class AlphabeticWithSpaceNullableValidator implements ConstraintValidator<ValidAlphabeticWithSpaceNullable, String> {
	
    @Override
    public void initialize(ValidAlphabeticWithSpaceNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String letter, ConstraintValidatorContext context) {   
        return (validateAlphabetic(letter));
    }
    
    private boolean validateAlphabetic(String letter) {
        return UValidator.validateNullableAlphabeticWithSpace(letter);
    }
    
}