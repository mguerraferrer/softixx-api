package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidDateTimeNullable;

public class DateTimeNullableValidator implements ConstraintValidator<ValidDateTimeNullable, String> {

	@Override
    public void initialize(ValidDateTimeNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {   
        return (validateDate(date));
    }
    
    private boolean validateDate(String date) {
    	return UValidator.validateNullableDateTime(date);
    }
    
}