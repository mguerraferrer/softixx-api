package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidNumericWithSpace;

public class NumericWithSpaceValidator implements ConstraintValidator<ValidNumericWithSpace, String> {

	@Override
    public void initialize(ValidNumericWithSpace constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {   
        return (validateNumber(number));
    }
    
    private boolean validateNumber(String number) {
        return UValidator.validateNumberWithSpace(number);
    }
}