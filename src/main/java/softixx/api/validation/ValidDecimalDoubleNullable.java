package softixx.api.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import softixx.api.validation.constraint.DecimalDoubleNullableValidator;

@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DecimalDoubleNullableValidator.class)
@Documented
public @interface ValidDecimalDoubleNullable {

    String message() default "Invalid format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
