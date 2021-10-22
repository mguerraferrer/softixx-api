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
import softixx.api.validation.ValidRequired.List;
import softixx.api.validator.constraint.RequiredValidator;

/**
 * The annotated element must not be {@code null} nor empty. Supported types
 * are:
 * <ul>
 * <li>{@code CharSequence} (length of character sequence is evaluated)</li>
 * <li>{@code Collection} (collection size is evaluated)</li>
 * <li>{@code Map} (map size is evaluated)</li>
 * <li>Array (array length is evaluated)</li>
 * </ul>
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@Documented
@Constraint(validatedBy = RequiredValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface ValidRequired {
	/**
	 * @return the error message template
	 */
	String message() default UValidator.REQUIRED;

	/**
	 * @return the name of the field in case it is necessary to use it in the error
	 *         message. Ex: The {field} field has an invalid format
	 */
	String field() default "";

	/**
	 * @return the groups the constraint belongs to
	 */
	Class<?>[] groups() default {};

	/**
	 * @return the payload associated to the constraint
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * Defines several {@code @ValidRequired} constraints on the same element.
	 *
	 * @see ValidRequired
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		ValidRequired[] value();
	}
}