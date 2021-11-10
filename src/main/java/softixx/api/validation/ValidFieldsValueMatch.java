package softixx.api.validation;

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

import softixx.api.validation.constraint.FieldsValueMatchValidator;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Documented
public @interface ValidFieldsValueMatch {

	String message() default "Fields values don't match!";
	
	Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
	String field();
	 
    String fieldMatch();
 
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    	ValidFieldsValueMatch[] value();
    }

}