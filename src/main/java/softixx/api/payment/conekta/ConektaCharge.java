package softixx.api.payment.conekta;

import org.json.JSONObject;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class ConektaCharge {
	private String id;
	@Default
	private String object = "charge";
	private Integer created_at;
	private String currency;
	private Integer amount;
	private String reference_id;
	private Boolean livemode;
	private ConektaPaymentMethod payment_method;
	private String order_id;
	
	public JSONObject charge(final ConektaCharge bean) {
		return new JSONObject(bean);
	}
	
	public ConektaCharge charge(final JSONObject charge) {
		if(charge != null) {
			
		}
		return null;
	}
}
