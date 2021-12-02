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
import softixx.api.validation.constraint.FutureOrPresentValidator;

/**
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@Documented
@Constraint(validatedBy = { FutureOrPresentValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface ValidFutureOrPresent {
	/**
	 * @return the error message template
	 */
	String message() default UValidator.INVALID_DATE_OUT_RANGE;

	/**
	 * @return the name of the field in case it is necessary to use it in the error
	 *         message. Ex: The {field} field has an invalid format
	 */
	String field() default "";

	/**
	 * @return the name of the api field in case it is necessary to use it in the
	 *         error response
	 */
	String apiField() default "";

	/**
	 * @return if true, the apiField instead of the field. <br>
	 *         Ex: If the field is myField and the apiField is my_field, then
	 *         my_field is returned.
	 */
	boolean showOnlyApiField() default false;

	/**
	 * @return if true, the time will be considered for parsing the date
	 */
	boolean parseTime() default false;

	/**
	 * @return the format to be used to parse a date. Default is "yyyy-MM-dd"
	 */
	String format() default "yyyy-MM-dd";

	/**
	 * @return the groups the constraint belongs to
	 */
	Class<?>[] groups() default {};

	/**
	 * @return the payload associated to the constraint
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * Defines several {@link ValidFutureOrPresent} annotations on the same element.
	 *
	 * @see ValidFutureOrPresent
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		ValidFutureOrPresent[] value();
	}

}