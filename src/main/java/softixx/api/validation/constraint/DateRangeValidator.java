package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import lombok.val;
import softixx.api.util.UValidator;
import softixx.api.validation.ValidDateRange;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {
	
	private String fromConst;
	private String toConst;
	private String message;
	private String format;
	private boolean parseTime;
	private boolean dateUtc;
	private boolean inclusive;
	
	@Override
	public void initialize(ValidDateRange constraintAnnotation) {
		fromConst = constraintAnnotation.from();
		toConst = constraintAnnotation.to();
		message = constraintAnnotation.message();
		format = constraintAnnotation.format();
		parseTime = constraintAnnotation.parseTime();
		dateUtc = constraintAnnotation.dateUtc();
		inclusive = constraintAnnotation.inclusive();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		val from = new BeanWrapperImpl(value).getPropertyValue(fromConst);
		val to = new BeanWrapperImpl(value).getPropertyValue(toConst);
		
		val isValid = UValidator.validateDateRange(String.valueOf(from), String.valueOf(to), format, parseTime, dateUtc, inclusive);
		if (!isValid) {
			context.buildConstraintViolationWithTemplate(message)
				   .addPropertyNode(toConst)
				   .addConstraintViolation()
				   .disableDefaultConstraintViolation();
			return false;
		} 
		return true;
	}

}