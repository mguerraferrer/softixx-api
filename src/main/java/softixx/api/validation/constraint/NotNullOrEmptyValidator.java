package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import softixx.api.validation.ValidNotNullOrEmpty;
import lombok.val;

/**
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
public class NotNullOrEmptyValidator implements ConstraintValidator<ValidNotNullOrEmpty, Object> {
	
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		if(ObjectUtils.isEmpty(obj)) {
			return false;
		}
		
		if (obj instanceof String) {
			val str = obj.toString().trim();
			return StringUtils.hasLength(str);
		}
		
		return true;
	}

}