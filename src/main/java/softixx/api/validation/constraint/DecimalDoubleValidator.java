package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidDecimalDouble;

public class DecimalDoubleValidator implements ConstraintValidator<ValidDecimalDouble, Double> {

	@Override
    public void initialize(ValidDecimalDouble constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(Double number, ConstraintValidatorContext context) {
        return (validateDecimal(number));
    }
    
    private boolean validateDecimal(Double number) {
    	return UValidator.validateDecimal(number);
    }
    
}