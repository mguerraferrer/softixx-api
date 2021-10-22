package softixx.api.payment.conekta;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConektaCustomer {
	private String customer_id;
	private String name;
	private String email;
	private String phone;
}
