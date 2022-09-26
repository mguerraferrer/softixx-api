package softixx.api.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.apache.commons.text.RandomStringGenerator;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordGenerator;
import org.passay.PasswordValidator;
import org.passay.WhitespaceRule;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.val;

public class UPassword {
	private UPassword() {
		throw new IllegalStateException("Utility class");
	}

    private static final int PWD_LENGTH = 2;

    public static String encryptedPassword(final String password) {
        val encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static String generateCommonTextPassword() {
        val pwString = generateRandomSpecialCharacters(PWD_LENGTH)
                		.concat(generateRandomNumbers(PWD_LENGTH))
                		.concat(generateRandomAlphabet(PWD_LENGTH, true))
                		.concat(generateRandomAlphabet(PWD_LENGTH, false))
                		.concat(generateRandomCharacters(PWD_LENGTH));

        val pwChars = pwString.chars().mapToObj(char.class::cast).collect(Collectors.toList());
        Collections.shuffle(pwChars);
        return pwChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

    private static String generateRandomSpecialCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45).build();
        return pwdGenerator.generate(length);
    }

    private static String generateRandomNumbers(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(48, 57).build();
        return pwdGenerator.generate(length);
    }

    private static String generateRandomAlphabet(int length, boolean lowerCase) {
        int low;
        int hi;
        if (lowerCase) {
            low = 97;
            hi = 122;
        } else {
            low = 65;
            hi = 90;
        }
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(low, hi).build();
        return pwdGenerator.generate(length);
    }

    private static String generateRandomCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(48, 57).build();
        return pwdGenerator.generate(length);
    }

    public static String passwordGenerator() {
    	val rules = Arrays.asList(
    		//##### Rule 1: At least one Upper-case character
    		new CharacterRule(EnglishCharacterData.UpperCase, 1),
    		//##### Rule 2: At least one Lower-case character
    		new CharacterRule(EnglishCharacterData.LowerCase, 1),
    		//##### Rule 3: at least one digit character
    		new CharacterRule(EnglishCharacterData.Digit, 1),
    		//##### Rule 4: At least one special character
            new CharacterRule(EnglishCharacterData.Special, 1));
    	
    	val generator = new PasswordGenerator();
    	//##### Generated password is 12 characters long, which complies with policy
    	return generator.generatePassword(12, rules);
    }
    
    public static boolean passwordValidator(final String password) {
    	if (UValidator.isNotEmpty(password)) {
	    	val validator = new PasswordValidator(Arrays.asList(
	    		//##### Rule 1: Password length should be in between 8 and 16 characters
	    		new LengthRule(8, 16),
	    		//##### Rule 2: No whitespace allowed
	    		new WhitespaceRule(),
	    		//##### Rule 3: At least one Upper-case character
	    		new CharacterRule(EnglishCharacterData.UpperCase, 1),
	    		//##### Rule 4: At least one Lower-case character
	    		new CharacterRule(EnglishCharacterData.LowerCase, 1),
	    		//##### Rule 5: At least one digit
	            new CharacterRule(EnglishCharacterData.Digit, 1),
	            //##### Rule 6: At least one special character
	            new CharacterRule(EnglishCharacterData.Special, 1),
	            //##### Rule 6: No more 5 numerical secuence (12345|56789)
	            new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
	            //##### Rule 7: No more 5 alphabetical secuence (abcde|stxyz)
	            new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
	            //##### Rule 8: No more 5 qwerty secuence (qwert|asdfg)
	            new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false)));
	        
	        val result = validator.validate(new PasswordData(password));
	        if (result.isValid()) {
	            return true;
	        }
    	}
        return false;
    }
}