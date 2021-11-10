package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidDate;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

	@Override
    public void initialize(ValidDate constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {   
        return (validateDate(date));
    }
    
    private boolean validateDate(String date) {
    	return UValidator.validateDate(date);
    }
    
}