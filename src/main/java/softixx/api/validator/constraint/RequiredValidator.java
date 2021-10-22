package softixx.api.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidRequired;


/**
 * This validator implements the {@link ValidRequired} interface
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 * @see ValidRequired	
 */
public class RequiredValidator implements ConstraintValidator<ValidRequired, Object> {
	
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		return UValidator.validateRequired(obj);
	}

}