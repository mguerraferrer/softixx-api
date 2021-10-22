package softixx.api.payment.conekta;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConektaItem {
	private String name;
	private String description;
	private Integer unit_price;
	private Integer quantity;
	private String sku;	
}
