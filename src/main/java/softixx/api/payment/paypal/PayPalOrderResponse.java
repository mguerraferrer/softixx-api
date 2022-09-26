package softixx.api.payment.paypal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayPalOrderResponse {
	private String id;
	private String status;

	public boolean validate(String orderId) {
		return id.equals(orderId) && status.equals("COMPLETED");
	}

}