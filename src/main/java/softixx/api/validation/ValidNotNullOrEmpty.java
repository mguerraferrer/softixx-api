package softixx.api.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import softixx.api.util.UValidator;
import softixx.api.validation.constraint.NotNullOrEmptyValidator;

/**
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@Documented
@Constraint(validatedBy = NotNullOrEmptyValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface ValidNotNullOrEmpty {
	
	String message() default UValidator.REQUIRED;
	
	String field() default "";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
}