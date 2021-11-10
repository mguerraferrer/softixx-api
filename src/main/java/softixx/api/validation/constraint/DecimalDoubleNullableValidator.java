package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidDecimalDoubleNullable;

public class DecimalDoubleNullableValidator implements ConstraintValidator<ValidDecimalDoubleNullable, Double> {

	@Override
    public void initialize(ValidDecimalDoubleNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(Double number, ConstraintValidatorContext context) {   
        return (validateDecimal(number));
    }
    
    private boolean validateDecimal(Double number) {
		return UValidator.validateNullableDecimal(number);
    }
    
}