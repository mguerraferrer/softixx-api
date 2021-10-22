package softixx.api.payload;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import softixx.api.util.UValidator;
import softixx.api.validation.ValidEmail;

@Data
public class EmailDto {

    @NotEmpty(message = UValidator.REQUIRED)
    @ValidEmail(message = UValidator.INVALID_EMAIL)
	private String email;
	
}