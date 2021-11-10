package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidNumericWithSpaceNullable;

public class NumericWithSpaceNullableValidator implements ConstraintValidator<ValidNumericWithSpaceNullable, String> {

	@Override
    public void initialize(ValidNumericWithSpaceNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {   
        return (validateNumber(number));
    }
    
    private boolean validateNumber(String number) {
        return UValidator.validateNullableNumberWithSpace(number);
    }
}