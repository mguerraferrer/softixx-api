package softixx.api.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import softixx.api.util.URandom;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateDto {
	@Default
	private String handle = URandom.randomBigInteger();
	private String carrierName;
	private String carrierLogoURL;
	private String serviceName;
	private String estimatedDeliveryDate;
	private String amount;
	private Double totalAmount;
	@Default
	private String currency = "MXN";
}
