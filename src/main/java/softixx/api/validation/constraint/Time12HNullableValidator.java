package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidTime12HNullable;

public class Time12HNullableValidator implements ConstraintValidator<ValidTime12HNullable, String> {

	@Override
    public void initialize(ValidTime12HNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String time, ConstraintValidatorContext context) {   
        return (validateTime(time));
    }
    
    private boolean validateTime(String time) {
    	return UValidator.validateNullableTime12H(time);
    }
    
}