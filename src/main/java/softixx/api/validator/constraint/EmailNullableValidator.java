package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidEmailNullable;

public class EmailNullableValidator implements ConstraintValidator<ValidEmailNullable, String> {

	@Override
    public void initialize(ValidEmailNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {   
        return (validateEmail(email));
    }
    
    private boolean validateEmail(String email) {
        return UValidator.validateNullableEmail(email);
    }
    
}