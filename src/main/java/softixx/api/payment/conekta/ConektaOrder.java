package softixx.api.payment.conekta;

import java.util.List;

import org.json.JSONObject;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class ConektaOrder {
	private String id;
	@Default
	private String object = "order";
	private String currency;
	private List<ConektaItem> line_items;
	private ConektaCustomer customer_info;
	private List<ConektaCharge> charges;
	
	public static JSONObject orderJSON(ConektaOrder bean) {
		return new JSONObject(bean);
	}
}
