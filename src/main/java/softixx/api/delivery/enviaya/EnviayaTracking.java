package softixx.api.delivery.enviaya;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnviayaTracking {
	private static final Logger log = LoggerFactory.getLogger(EnviayaTracking.class);
	
	@JsonProperty("api_key")
	private String apiKey;
	private String carrier;
	@JsonProperty("carrier_account")
	private String carrierAccount;
	@JsonProperty("shipment_number")
	private String shipment_number;
	
	protected static String toJsonString(final EnviayaTracking obj) {
		try {
			
			val mapper = new ObjectMapper();
	        return mapper.writeValueAsString(obj);
			
		} catch (Exception e) {
			log.error("EnviayaTracking toJsonString error {}", e.getMessage());
		}
    	return null;
    }
    
    protected static EnviayaTracking fromJsonString(final String json) {
    	try {
    		
    		val mapper = new ObjectMapper();
            return mapper.readValue(json, EnviayaTracking.class);
    		
		} catch (Exception e) {
			log.error("EnviayaTracking fromJsonString error {}", e.getMessage());
		}
    	return null;
    }
    
    @Data
    @NoArgsConstructor
    public static class EnviayaTrackingResponse {
    	private static final Logger log = LoggerFactory.getLogger(EnviayaTrackingResponse.class);
    	
    	private String status;
    	@JsonProperty("enviaya_shipment_number")
    	private String enviayaShipmentNumber;
    	@JsonProperty("carrier_tracking_number")
    	private String carrierTrackingNumber;
    	@JsonProperty("estimated_delivery_date")
    	private String estimatedDeliveryDate; 	// 19/12/2017
    	@JsonProperty("expected_delivery_date")
    	private String expectedDeliveryDate;  	// 28/12/2017",
    	@JsonProperty("delivery_date")
    	private String deliveryDate; 			// 2017-12-21T02:55:00-06:00",
    	@JsonProperty("pickup_date")
    	private String pickupDate;				// 2017-12-13T07:57:39-06:00",
    	@JsonProperty("shipment_status")
    	private String shipmentStatus;
    	@JsonProperty("event_code")
    	private Integer eventCode;
    	@JsonProperty("event_description")
    	private String eventDescription;
    	private String event;
    	@JsonProperty("status_code")
    	private Integer statusCode;
    	@JsonProperty("sub_event_code")
    	private Integer subEventCode;
    	@JsonProperty("sub_event")
    	private String subEvent;
    	@JsonProperty("sub_event_description")
    	private String subEventDescription;
    	
    	protected static String toJsonString(final EnviayaTrackingResponse obj) {
    		try {
    			
    			val mapper = new ObjectMapper();
    	        return mapper.writeValueAsString(obj);
    			
    		} catch (Exception e) {
    			log.error("EnviayaTrackingResponse toJsonString error {}", e.getMessage());
    		}
        	return null;
        }
        
        protected static EnviayaTrackingResponse fromJsonString(final String json) {
        	try {
        		
        		val mapper = new ObjectMapper();
                return mapper.readValue(json, EnviayaTrackingResponse.class);
        		
    		} catch (Exception e) {
    			log.error("EnviayaTrackingResponse fromJsonString error {}", e.getMessage());
    		}
        	return null;
        }
    }
}
