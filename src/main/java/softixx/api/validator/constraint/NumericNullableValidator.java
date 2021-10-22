package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidNumericNullable;

public class NumericNullableValidator implements ConstraintValidator<ValidNumericNullable, String> {

    @Override
    public void initialize(ValidNumericNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {   
        return (validateNumber(number));
    }
    
    private boolean validateNumber(String number) {
        return UValidator.validateNullableNumber(number);
    }
}