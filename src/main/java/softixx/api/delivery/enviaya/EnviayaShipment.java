package softixx.api.delivery.enviaya;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class EnviayaShipment {
	private static final Logger log = LoggerFactory.getLogger(EnviayaShipmentBooking.class);
	
	@JsonProperty("shipment_type")
    private String shipmentType;
	@JsonProperty("shipment_date")
	private String shipmentDate;
	@JsonProperty("shipment_status")
	private String shipmentStatus;
	@JsonProperty("status_message")
	private String statusMessage;
	private String id;
	@JsonProperty("insured_amount")
    private Double insuredAmount;
	@JsonProperty("insured_amount_currency")
    private String insuredAmountCurrency;
    private String content;
    private String carrier;
    @JsonProperty("carrier_shipment_number")
    private String carrierShipmentNumber;
    @JsonProperty("carrier_service_code")
    private String carrierServiceCode;
    @JsonProperty("enviaya_service_code")
    private String enviayaServiceCode;
    private String label;
    @JsonProperty("label_format")
    private String labelFormat;
    @JsonProperty("label_file_type")
    private String labelFileType;
    @JsonProperty("label_url")
    private String labelURL;
    @JsonProperty("label_share_link")
    private String labelShareLink;
    @JsonProperty("parcels")
    @Singular
    private List<EnviayaParcel> parcels;
    @Singular(value = "rate")
    private List<EnviayaRate> rate;
    @Singular
    private List<String> warnings;
    @Singular
    private List<String> references;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("qr_code_url")
    private String qrCodeUrl;
    private String error;
    @Singular(value = "errors")
    private List<String> errors;
    
    protected static String toJsonString(final EnviayaShipment obj) {
		try {
			
			val mapper = new ObjectMapper();
	        return mapper.writeValueAsString(obj);
			
		} catch (Exception e) {
			log.error("EnviayaShipment toJsonString error - {}", e.getMessage());
		}
    	return null;
    }
    
    protected static EnviayaShipment fromJsonString(final String json) {
    	try {
    		
    		val mapper = new ObjectMapper();
            return mapper.readValue(json, EnviayaShipment.class);
    		
		} catch (Exception e) {
			log.error("EnviayaShipment fromJsonString error - {}", e.getMessage());
		}
    	return null;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnviayaShipmentBooking {
    	private static final Logger log = LoggerFactory.getLogger(EnviayaShipmentBooking.class);
    	
    	@JsonProperty("api_key")
    	private String apiKey;
    	@JsonProperty("enviaya_account")
    	private String enviayaAccount;
    	@JsonProperty("carrier_account")
        private Object carrierAccount;
        private String carrier;
        @JsonProperty("carrier_service_code")
        private String carrierServiceCode;
        @JsonProperty("rate_id")
        private Integer rateId;
        @JsonProperty("origin_direction")
        private EnviayaDirection origin;
        @JsonProperty("destination_direction")
        private EnviayaDirection destination;
        @JsonProperty("labelFormat")
        private String labelFormat;
        
        protected static String toJsonString(final EnviayaShipmentBooking obj) {
    		try {
    			
    			val mapper = new ObjectMapper();
    	        return mapper.writeValueAsString(obj);
    			
    		} catch (Exception e) {
    			log.error("EnviayaShipmentBooking toJsonString error - {}", e.getMessage());
    		}
        	return null;
        }
        
        protected static EnviayaShipmentBooking fromJsonString(final String json) {
        	try {
        		
        		val mapper = new ObjectMapper();
                return mapper.readValue(json, EnviayaShipmentBooking.class);
        		
    		} catch (Exception e) {
    			log.error("EnviayaShipmentBooking fromJsonString error - {}", e.getMessage());
    		}
        	return null;
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnviayaShipmentData {
    	@JsonProperty("api_key")
    	private String apiKey;
    	@JsonProperty("enviaya_account")
    	private String enviayaAccount;
    	@JsonProperty("enviaya_id")
    	private String enviayaId;
    	@JsonProperty("shipment_id")
    	private String shipmentId;
    	
    	protected static String toJsonString(final EnviayaShipmentData obj) throws JsonProcessingException {
        	val mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        }
        
        protected static EnviayaShipmentData fromJsonString(final String json) throws IOException {
        	val mapper = new ObjectMapper();
            return mapper.readValue(json, EnviayaShipmentData.class);
        }
    }
    
    public enum ShipmentType {
    	Document, Package
    }
    
    public enum LabelFormat {
    	Letter, ZPL, EPL;
    }
    
}