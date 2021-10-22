package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidDecimalNullable;

public class DecimalNullableValidator implements ConstraintValidator<ValidDecimalNullable, String> {

	@Override
    public void initialize(ValidDecimalNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {   
        return (validateDecimal(number));
    }
    
    private boolean validateDecimal(String number) {
		return UValidator.validateNullableDecimal(number);
    }
    
}