package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidTime24HNullable;

public class Time24HNullableValidator implements ConstraintValidator<ValidTime24HNullable, String> {

	@Override
    public void initialize(ValidTime24HNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String time, ConstraintValidatorContext context) {   
        return (validateTime(time));
    }
    
    private boolean validateTime(String time) {
    	return UValidator.validateNullableTime24H(time);
    }
    
}