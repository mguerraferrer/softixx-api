package softixx.api.delivery.enviaya;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.val;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnviayaPickup {
	private static final Logger log = LoggerFactory.getLogger(EnviayaPickup.class);
	
	@JsonProperty("enviaya_account")
	private String enviayaAccount;
	@JsonProperty("carrier_account")
    private String carrierAccount;
	@JsonProperty("api_key")
    private String apiKey;
    private String carrier;
    @JsonProperty("pickup_date")
    private String pickupDate;
    @JsonProperty("schedule_from")
    private String scheduleFrom;
    @JsonProperty("schedule_to")
    private String scheduleTo;
    private EnviayaDirection direction;
    private EnviayaShipment shipment;
    private Integer id;
    
    protected static String toJsonString(final EnviayaPickup obj) {
		try {
			
			val mapper = new ObjectMapper();
	        return mapper.writeValueAsString(obj);
			
		} catch (Exception e) {
			log.error("EnviayaPickup toJsonString error {}", e.getMessage());
		}
    	return null;
    }
    
    protected static EnviayaPickup fromJsonString(final String json) {
    	try {
    		
    		val mapper = new ObjectMapper();
            return mapper.readValue(json, EnviayaPickup.class);
    		
		} catch (Exception e) {
			log.error("EnviayaPickup fromJsonString error {}", e.getMessage());
		}
    	return null;
    }
    
    @Data
    @NoArgsConstructor
    public static class EnviayaPickupResponse {
    	private static final Logger log = LoggerFactory.getLogger(EnviayaPickupResponse.class);
    	
        private String status;
        @Singular
        private List<String> messages;
        @JsonProperty("carrier_pickup_confirmation_number")
        private String carrierPickupConfirmationNumber;
        @JsonProperty("pickup_date")
        private String pickupDate;
        @JsonProperty("enviaya_pickup_id")
        private Integer enviayaPickupID;
        @JsonProperty("pickup_status")
        private String pickupStatus;
        
        protected static String toJsonString(final EnviayaPickupResponse obj) {
    		try {
    			
    			val mapper = new ObjectMapper();
    	        return mapper.writeValueAsString(obj);
    			
    		} catch (Exception e) {
    			log.error("EnviayaPickupResponse toJsonString error {}", e.getMessage());
    		}
        	return null;
        }
        
        protected static EnviayaPickupResponse fromJsonString(final String json) {
        	try {
        		
        		val mapper = new ObjectMapper();
                return mapper.readValue(json, EnviayaPickupResponse.class);
        		
    		} catch (Exception e) {
    			log.error("EnviayaPickupResponse fromJsonString error {}", e.getMessage());
    		}
        	return null;
        }
    }
}
