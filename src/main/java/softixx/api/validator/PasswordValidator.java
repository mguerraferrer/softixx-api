package softixx.api.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import softixx.api.util.UValidator;

import java.util.List;

public class PasswordValidator {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isValid(final String password, final String oldPassword) {
        if(UValidator.isNotNull(password) && UValidator.isNotNull(oldPassword)) {
            return passwordEncoder.matches(password, oldPassword);
        }
        return false;
    }

    public boolean isValid(final String password, final List<String> oldPasswords) {
        if(UValidator.isNotNull(password) && !oldPasswords.isEmpty()) {
            return oldPasswords.stream()
                               .filter(item -> passwordEncoder.matches(password, item))
                               .findFirst().isPresent();
        }
        return false;
    }
}
