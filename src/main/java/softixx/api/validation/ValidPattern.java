package softixx.api.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import softixx.api.util.UValidator;
import softixx.api.validation.ValidPattern.List;
import softixx.api.validator.constraint.PatternValidator;

/**
 * The annotated {@code CharSequence} must match the specified regular
 * expression. The regular expression follows the Java regular expression
 * conventions see {@link java.util.regex.Pattern}.
 * <p>
 * Accepts {@code CharSequence}. {@code null} elements are considered valid.
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = PatternValidator.class)
@Repeatable(List.class)
public @interface ValidPattern {
	/**
	 * @return the regular expression to match
	 */
	String regexp();

	/**
	 * @return the error message template
	 */
	String message() default UValidator.INVALID_FIELD;

	/**
	 * @return the name of the field in case it is necessary to use it in the error
	 *         message. Ex: The {field} field has an invalid format
	 */
	String field() default "";

	/**
	 * @return the format considered as valid in case it is necessary to use it in
	 *         the error message. Ex: The {field} field has an invalid format.
	 *         Consider as valid format {format}
	 */
	String format() default "";

	/**
	 * @return the groups the constraint belongs to
	 */
	Class<?>[] groups() default {};
	
	/**
	 * @return the payload associated to the constraint
	 */
	Class<? extends Payload>[] payload() default {};
	
	/**
	 * Defines several {@link ValidPattern} annotations on the same element.
	 *
	 * @see ValidPattern
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		ValidPattern[] value();
	}
}