package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidNumeric;

public class NumericValidator implements ConstraintValidator<ValidNumeric, String> {

	@Override
    public void initialize(ValidNumeric constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {   
        return (validateNumber(number));
    }
    
    private boolean validateNumber(String number) {
        return UValidator.validateNumber(number);
    }
}