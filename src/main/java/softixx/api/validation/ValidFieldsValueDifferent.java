package softixx.api.validation;

import softixx.api.validator.constraint.FieldsValueDifferentValidator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = FieldsValueDifferentValidator.class)
@Documented
public @interface ValidFieldsValueDifferent {

	String message() default "Fields values must be different!";
	
	Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
	
	String field();
	 
    String fieldDifferent();
 
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    	ValidFieldsValueDifferent[] value();
    }

}