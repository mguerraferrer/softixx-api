package softixx.api.delivery.dostavista;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.val;
import softixx.api.delivery.dostavista.DostavistaOrderPoint.DostavistaOrderPointRequest;
import lombok.Builder.Default;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class DostavistaRequest {
	private static final Logger log = LoggerFactory.getLogger(DostavistaRequest.class);
	
	private String matter;
	@JsonProperty("vehicle_type_id")
	@Default
    private Integer vehicleTypeId = 8;
	@JsonProperty("total_weight_kg")
	@Default
    private Integer totalWeightKg = 1;
	@JsonProperty("insurance_amount")
	@Default
    private String insuranceAmount = "0.00";
	@JsonProperty("is_client_notification_enabled")
	@Default
    private boolean isClientNotificationEnabled = false;
	@JsonProperty("is_contact_person_notification_enabled")
	@Default
    private boolean isContactPersonNotificationEnabled = false;
	@JsonProperty("is_route_optimizer_enabled")
	@Default
    private boolean isRouteOptimizerEnabled = false;
	@JsonProperty("loaders_count")
	@Default
    private Integer loadersCount = 0;
	@JsonProperty("backpayment_details")
	@Default
    private String backpaymentDetails = null;
	@JsonProperty("is_motobox_required")
	@Default
    private boolean isMotoboxRequired = false;
	@JsonProperty("payment_method")
	@Default
    private String paymentMethod = null;
	@JsonProperty("bank_card_id")
	@Default
    private String bankCardId = null;
	@Singular
    private List<DostavistaOrderPointRequest> points;
    
    protected static String toJsonString(final DostavistaRequest obj) {
		try {
			
			val mapper = new ObjectMapper();
	        return mapper.writeValueAsString(obj);
			
		} catch (Exception e) {
			log.error("DostavistaRequest#toJsonString error - {}", e.getMessage());
		}
    	return null;
    }
    
    protected static DostavistaRequest fromJsonString(final String json) {
    	try {
    		
    		val mapper = new ObjectMapper();
            return mapper.readValue(json, DostavistaRequest.class);
    		
		} catch (Exception e) {
			log.error("DostavistaRequest#fromJsonString error - {}", e.getMessage());
		}
    	return null;
    }
}
