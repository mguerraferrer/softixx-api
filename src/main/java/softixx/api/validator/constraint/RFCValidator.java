package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidRFC;

public class RFCValidator implements ConstraintValidator<ValidRFC, String> {

	@Override
    public void initialize(ValidRFC constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String rfc, ConstraintValidatorContext context) {   
        return (validateRFC(rfc));
    }
    
    private boolean validateRFC(String rfc) {
        return UValidator.validateRFC(rfc);
    }
    
}