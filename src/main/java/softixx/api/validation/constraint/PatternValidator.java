package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidPattern;


/**
 * This validator implements the {@link ValidPattern} interface
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 * @see ValidPattern
 *
 */
public class PatternValidator implements ConstraintValidator<ValidPattern, String> {
	
	private String regexp;
	
	@Override
	public void initialize(ValidPattern constraintAnnotation) {
		regexp = constraintAnnotation.regexp();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return UValidator.validatePattern(value, regexp);
	}

}