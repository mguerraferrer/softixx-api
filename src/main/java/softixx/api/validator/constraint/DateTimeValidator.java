package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidDateTime;

public class DateTimeValidator implements ConstraintValidator<ValidDateTime, String> {

	@Override
    public void initialize(ValidDateTime constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {   
        return (validateDate(date));
    }
    
    private boolean validateDate(String date) {
    	return UValidator.validateDateTime(date);
    }
    
}