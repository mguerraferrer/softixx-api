package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidAlphabetic;

public class AlphabeticValidator implements ConstraintValidator<ValidAlphabetic, String> {

    @Override
    public void initialize(ValidAlphabetic constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String letter, ConstraintValidatorContext context) {   
        return (validateAlphabetic(letter));
    }
    
    private boolean validateAlphabetic(String letter) {
        return UValidator.validateAlphabetic(letter);
    }
    
}