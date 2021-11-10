package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidIntNumeric;

public class NumericIntValidator implements ConstraintValidator<ValidIntNumeric, Integer> {

	@Override
    public void initialize(ValidIntNumeric constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(Integer number, ConstraintValidatorContext context) {
        return (validateNumber(number));
    }
    
    private boolean validateNumber(Integer number) {
        return UValidator.validateIntNumber(number);
    }
}