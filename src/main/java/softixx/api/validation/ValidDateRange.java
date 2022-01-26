package softixx.api.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import softixx.api.util.UValidator;
import softixx.api.validation.constraint.DateRangeValidator;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface ValidDateRange {

	/**
	 * @return the error message template
	 */
	String message() default UValidator.INVALID_DATE_OUT_RANGE;

	/**
	 * @return the range start date
	 */
	String from();

	/**
	 * @return the range end date
	 */
	String to();

	/**
	 * @return if true, the time will be considered for parsing the date
	 */
	boolean parseTime() default false;

	/**
	 * @return the format to be used to parse a date. Default is "yyyy-MM-dd"
	 */
	String format() default "yyyy-MM-dd";

	/**
	 * @return if true, the UTC date wil be used to validate
	 */
	boolean dateUtc() default false;

	/**
	 * @return if true, the range end date can be greater than or equal to the range
	 *         start date. By default, the range end date must be strictly greater
	 *         than the range start date
	 */
	boolean inclusive() default false;

	/**
	 * @return the groups the constraint belongs to
	 */
	Class<?>[] groups() default {};

	/**
	 * @return the payload associated to the constraint
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * Defines several {@link ValidDateRange} annotations on the same element.
	 *
	 * @see ValidDateRange
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@interface List {
		ValidDateRange[] value();
	}

}