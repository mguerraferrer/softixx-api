package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidTime12H;

public class Time12HValidator implements ConstraintValidator<ValidTime12H, String> {

	@Override
    public void initialize(ValidTime12H constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String time, ConstraintValidatorContext context) {   
        return (validateTime(time));
    }
    
    private boolean validateTime(String time) {
    	return UValidator.validateTime12H(time);
    }
    
}