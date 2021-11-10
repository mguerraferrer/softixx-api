package softixx.api.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import softixx.api.util.UPassword;
import softixx.api.validation.ValidPassword;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(final ValidPassword arg0) {

    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        return UPassword.passwordValidator(password);
        //context.disableDefaultConstraintViolation();
        //context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result))).addConstraintViolation();
    }

}
