package softixx.api.payload;

import javax.validation.GroupSequence;

import lombok.Data;
import softixx.api.util.UValidator;
import softixx.api.validation.ValidEmail;
import softixx.api.validation.ValidNotNullOrEmpty;
import softixx.api.validation.group.Group1;
import softixx.api.validation.group.Group2;

@Data
@GroupSequence({ NewsletterDto.class, Group1.class, Group2.class })
public class NewsletterDto {

	@ValidNotNullOrEmpty(groups = Group1.class, message = UValidator.REQUIRED)
	@ValidEmail(groups = Group2.class, message = UValidator.INVALID_EMAIL)
	private String newsletterEmail;

}