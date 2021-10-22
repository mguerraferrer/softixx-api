package softixx.api.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;
import softixx.api.util.UValidator;
import softixx.api.validation.ValidEmail;
import softixx.api.validation.ValidFieldsValueMatch;
import softixx.api.validation.ValidPassword;

@Data
@NoArgsConstructor
@ValidFieldsValueMatch(field = "password", fieldMatch = "repassword", message = UValidator.INVALID_PASSWORD_MATCHING)
public class UserRecoveryPasswordDto {

	@NotBlank(message = UValidator.REQUIRED)
	private String code;
	
	@NotEmpty(message = UValidator.REQUIRED)
    @ValidEmail(message = UValidator.INVALID_EMAIL)
	private String email;
	
	@NotEmpty(message = UValidator.REQUIRED)
    @ValidPassword(message = UValidator.INVALID_PASSWORD)
	private String password;
	
	@NotEmpty(message = UValidator.REQUIRED)
    private String repassword;
	
}