package softixx.api.delivery.dostavista;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@JsonInclude(Include.NON_NULL)
public class DostavistaOrderPoint {
	private static final Logger log = LoggerFactory.getLogger(DostavistaOrderPoint.class);
	
	@JsonProperty("point_type")
	private String pointType;
	@JsonProperty("point_id")
    private Integer pointId;
	@JsonProperty("delivery_id")
    private Integer deliveryId;
	@JsonProperty("client_order_id")
    private Integer clientOrderId;
    private String address;
    private String latitude;
    private String longitude;
    @JsonProperty("required_start_datetime")
    private String requiredStartDatetime;
    @JsonProperty("required_finish_datetime")
    private String requiredFinishDatetime;
    @JsonProperty("arrival_start_datetime")
    private String arrivalStartDatetime;
    @JsonProperty("arrival_finish_datetime")
    private String arrivalFinishDatetime;
    @JsonProperty("estimated_arrival_datetime")
    private String estimatedArrivalDatetime;
    @JsonProperty("courier_visit_datetime")
    private String courierVisitDatetime;
    @JsonProperty("contact_person")
    private DostavistaContactPerson contactPerson;
    @JsonProperty("taking_amount")
    private String takingAmount;
    @JsonProperty("buyout_amount")
    private String buyoutAmount;
    private String note;
    @Singular
    private List<String> packages;
    @JsonProperty("is_cod_cash_voucher_required")
    private boolean isCodCashVoucherRequired;
    @JsonProperty("place_photo_url")
    private String placePhotoUrl;
    @JsonProperty("sign_photo_url")
    private String signPhotoUrl;
    @JsonProperty("tracking_url")
    private String trackingUrl;
    private String checkin;
    @JsonProperty("is_order_payment_here")
    private boolean isOrderPaymentHere;
    @JsonProperty("building_number")
    private String buildingNumber;
    @JsonProperty("entrance_number")
    private String entranceNumber;
    @JsonProperty("intercom_code")
    private String intercomCode;
    @JsonProperty("floor_number")
    private String floorNumber;
    @JsonProperty("apartment_number")
    private String apartmentNumber;
    @JsonProperty("invisible_mile_navigation_instructions")
    private String invisibleMileNavigationInstructions;
    
    protected static String toJsonString(final DostavistaOrderPoint obj) {
		try {
			
			val mapper = new ObjectMapper();
	        return mapper.writeValueAsString(obj);
			
		} catch (Exception e) {
			log.error("DostavistaOrderPoint#toJsonString error {}", e.getMessage());
		}
    	return null;
    }
    
    protected static DostavistaOrderPoint fromJsonString(final String json) {
    	try {
    		
    		val mapper = new ObjectMapper();
            return mapper.readValue(json, DostavistaOrderPoint.class);
    		
		} catch (Exception e) {
			log.error("DostavistaOrderPoint#fromJsonString error {}", e.getMessage());
		}
    	return null;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(Include.NON_NULL)
    public static class DostavistaOrderPointRequest {
    	private String address;
    	@JsonProperty("contact_person")
        private DostavistaContactPerson contactPerson;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(Include.NON_NULL)
    public static class DostavistaContactPerson {
    	private String name;
        private String phone;
    }
}
