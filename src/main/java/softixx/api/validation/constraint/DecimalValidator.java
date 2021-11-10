package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidDecimal;

public class DecimalValidator implements ConstraintValidator<ValidDecimal, String> {

	@Override
    public void initialize(ValidDecimal constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        return (validateDecimal(number));
    }
    
    private boolean validateDecimal(String number) {
    	return UValidator.validateDecimal(number);
    }
    
}