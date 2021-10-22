package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidIntNumericNullable;

public class NumericIntNullableValidator implements ConstraintValidator<ValidIntNumericNullable, Integer> {

    @Override
    public void initialize(ValidIntNumericNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(Integer number, ConstraintValidatorContext context) {   
        return (validateNumber(number));
    }
    
    private boolean validateNumber(Integer number) {
        return UValidator.validateIntNullableNumber(number);
    }
}