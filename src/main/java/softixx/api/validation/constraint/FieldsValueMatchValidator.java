package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import softixx.api.validation.ValidFieldsValueMatch;

public class FieldsValueMatchValidator implements ConstraintValidator<ValidFieldsValueMatch, Object> {

	private String field;
    private String fieldMatch;
    private String message;
    
	@Override
    public void initialize(ValidFieldsValueMatch constraintAnnotation) {
		field = constraintAnnotation.field();
        fieldMatch = constraintAnnotation.fieldMatch();
        message = constraintAnnotation.message();
	}
    
	@Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
		Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
		Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
		
		boolean valid = true;
		if (fieldValue != null && fieldMatchValue != null) {
            valid = fieldValue.equals(fieldMatchValue);
            if (!valid) {
            	context.buildConstraintViolationWithTemplate(message)
            		   .addPropertyNode(fieldMatch)
            		   .addConstraintViolation()
            		   .disableDefaultConstraintViolation();
            }
        }
		return valid;
    }
}