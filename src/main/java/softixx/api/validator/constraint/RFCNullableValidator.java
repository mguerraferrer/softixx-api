package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidRFCNullable;

public class RFCNullableValidator implements ConstraintValidator<ValidRFCNullable, String> {

	@Override
    public void initialize(ValidRFCNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String rfc, ConstraintValidatorContext context) {   
        return (validateRFC(rfc));
    }
    
    private boolean validateRFC(String rfc) {
        return UValidator.validateNullableRFC(rfc);
    }
    
}