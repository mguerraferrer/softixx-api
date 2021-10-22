package softixx.api.validation;

import softixx.api.validator.constraint.AlphabeticWithSpaceNullableValidator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = AlphabeticWithSpaceNullableValidator.class)
@Documented
public @interface ValidAlphabeticWithSpaceNullable {

    String message() default "This field only accepts letters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
