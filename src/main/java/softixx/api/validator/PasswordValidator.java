package softixx.api.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import softixx.api.util.UValidator;

@Slf4j
public class PasswordValidator {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean isMatch(final String rawPassword, final String encodedPassword) {
		try {
			
			val encoder = new BCryptPasswordEncoder();
			return encoder.matches(rawPassword, encodedPassword);
			
		} catch (Exception e) {
			log.error("PasswordValidator#isMatch error {}", e.getMessage());
		}
		return false;
	}

	public boolean isValid(final String password, final String oldPassword) {
		if (UValidator.isNotNull(password) && UValidator.isNotNull(oldPassword)) {
			return passwordEncoder.matches(password, oldPassword);
		}
		return false;
	}

	public boolean isValid(final String password, final List<String> oldPasswords) {
		if (UValidator.isNotNull(password) && !oldPasswords.isEmpty()) {
			return oldPasswords.stream().anyMatch(item -> passwordEncoder.matches(password, item));
		}
		return false;
	}
}
