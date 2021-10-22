package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidTime24H;

public class Time24HValidator implements ConstraintValidator<ValidTime24H, String> {

	@Override
    public void initialize(ValidTime24H constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String time, ConstraintValidatorContext context) {   
        return (validateTime(time));
    }
    
    private boolean validateTime(String time) {
    	return UValidator.validateTime24H(time);
    }
    
}