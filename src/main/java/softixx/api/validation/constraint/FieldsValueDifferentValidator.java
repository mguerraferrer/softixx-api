package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import softixx.api.validation.ValidFieldsValueDifferent;

public class FieldsValueDifferentValidator implements ConstraintValidator<ValidFieldsValueDifferent, Object> {

	private String field;
    private String fieldDifferent;
    private String message;
    
	@Override
    public void initialize(ValidFieldsValueDifferent constraintAnnotation) {
		field = constraintAnnotation.field();
		fieldDifferent = constraintAnnotation.fieldDifferent();
        message = constraintAnnotation.message();
	}
    
	@Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
		Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
		Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldDifferent);
		
		boolean valid = true;
		if (fieldValue != null && fieldMatchValue != null) {
            valid = !fieldValue.equals(fieldMatchValue);
            if (!valid) {
            	context.buildConstraintViolationWithTemplate(message)
            		   .addPropertyNode(fieldDifferent)
            		   .addConstraintViolation()
            		   .disableDefaultConstraintViolation();
            }
        }
		return valid;
    }
}