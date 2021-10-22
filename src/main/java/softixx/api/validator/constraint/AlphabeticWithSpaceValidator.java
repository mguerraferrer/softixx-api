package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidAlphabeticWithSpace;

public class AlphabeticWithSpaceValidator implements ConstraintValidator<ValidAlphabeticWithSpace, String> {

	@Override
    public void initialize(ValidAlphabeticWithSpace constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String letter, ConstraintValidatorContext context) {   
        return (validateAlphabetic(letter));
    }
    
    private boolean validateAlphabetic(String letter) {
        return UValidator.validateAlphabeticWithSpace(letter);
    }
    
}