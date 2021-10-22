package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidEmail;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	@Override
    public void initialize(ValidEmail constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {   
        return (validateEmail(email));
    }
    
    private boolean validateEmail(String email) {
        return UValidator.validateEmail(email);
    }
    
}