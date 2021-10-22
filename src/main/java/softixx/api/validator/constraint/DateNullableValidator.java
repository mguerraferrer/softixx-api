package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidDateNullable;

public class DateNullableValidator implements ConstraintValidator<ValidDateNullable, String> {

	@Override
    public void initialize(ValidDateNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {   
        return (validateDate(date));
    }
    
    private boolean validateDate(String date) {
    	return UValidator.validateNullableDate(date);
    }
    
}