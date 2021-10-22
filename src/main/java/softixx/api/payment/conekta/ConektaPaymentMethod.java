package softixx.api.payment.conekta;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class ConektaPaymentMethod {
	@Default
	private String type = "card";
	private String token_id;
}
