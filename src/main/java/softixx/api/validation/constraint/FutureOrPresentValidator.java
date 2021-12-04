package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.ObjectUtils;

import lombok.val;
import softixx.api.util.UValidator;
import softixx.api.validation.ValidFutureOrPresent;

/**
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
public class FutureOrPresentValidator implements ConstraintValidator<ValidFutureOrPresent, String> {
	
	private String format;
	private boolean parseTime;
	private boolean useUtc;
	
	@Override
	public void initialize(ValidFutureOrPresent constraintAnnotation) {
		format = constraintAnnotation.format();
		parseTime = constraintAnnotation.parseTime();
		useUtc = constraintAnnotation.useUtc();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(ObjectUtils.isEmpty(value)) {
			return true;
		}
		
		val str = value.trim();
		return UValidator.validateFutureOrPresent(str, format, parseTime, useUtc);
	}

}