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
public class NotNullOrEmptyValidator implements ConstraintValidator<ValidNotNullOrEmpty, String> {
	
	@Override
	public boolean isValid(String text, ConstraintValidatorContext context) {
		if(ObjectUtils.isEmpty(text)) {
			return false;
		}
		
		val str = text.trim();
		return StringUtils.hasLength(str);
	}

}