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
public class DostavistaOrder {
	private static final Logger log = LoggerFactory.getLogger(DostavistaOrder.class);
	
	@JsonProperty("order_id")
	private Integer orderId;
	@JsonProperty("order_name")
    private String orderName;
	@JsonProperty("vehicle_type_id")
    private Integer vehicleTypeId;
	@JsonProperty("created_datetime")
    private String createdDatetime;
	@JsonProperty("finish_datetime")
    private Object finishDatetime;
    private String status;
    @JsonProperty("status_description")
    private String statusDescription;
    private String matter;
    @JsonProperty("total_weight_kg")
    private Integer totalWeightKg;
    @JsonProperty("is_client_notification_enabled")
    private boolean isClientNotificationEnabled;
    @JsonProperty("is_contact_person_notification_enabled")
    private boolean isContactPersonNotificationEnabled;
    @JsonProperty("loaders_count")
    private Integer loadersCount;
    @JsonProperty("backpayment_details")
    private Object backpaymentDetails;
    @Singular
    private List<DostavistaOrderPoint> points;
    @JsonProperty("payment_amount")
    private String paymentAmount;
    @JsonProperty("delivery_fee_amount")
    private String deliveryFeeAmount;
    @JsonProperty("intercity_delivery_fee_amount")
    private String intercityDeliveryFeeAmount;
    @JsonProperty("weight_fee_amount")
    private String weightFeeAmount;
    @JsonProperty("insurance_amount")
    private String insuranceAmount;
    @JsonProperty("insurance_fee_amount")
    private String insuranceFeeAmount;
    @JsonProperty("loading_fee_amount")
    private String loadingFeeAmount;
    @JsonProperty("money_transfer_fee_amount")
    private String moneyTransferFeeAmount;
    @JsonProperty("suburban_delivery_fee_amount")
    private String suburbanDeliveryFeeAmount;
    @JsonProperty("overnight_fee_amount")
    private String overnightFeeAmount;
    @JsonProperty("discount_amount")
    private String discountAmount;
    @JsonProperty("backpayment_amount")
    private String backpaymentAmount;
    @JsonProperty("cod_fee_amount")
    private String codFeeAmount;
    @JsonProperty("backpayment_photo_url")
    private Object backpaymentPhotoUrl;
    @JsonProperty("itinerary_document_url")
    private Object itineraryDocumentUrl;
    @JsonProperty("waybill_document_url")
    private Object waybillDocumentUrl;
    private Object courier;
    @JsonProperty("is_motobox_required")
    private boolean isMotoboxRequired;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("bank_card_id")
    private Object bankCardId;
    
    protected static String toJsonString(final DostavistaOrder obj) {
		try {
			
			val mapper = new ObjectMapper();
	        return mapper.writeValueAsString(obj);
			
		} catch (Exception e) {
			log.error("DostavistaOrder#toJsonString error {}", e.getMessage());
		}
    	return null;
    }
    
    protected static DostavistaOrder fromJsonString(final String json) {
    	try {
    		
    		val mapper = new ObjectMapper();
            return mapper.readValue(json, DostavistaOrder.class);
    		
		} catch (Exception e) {
			log.error("DostavistaOrder#fromJsonString error {}", e.getMessage());
		}
    	return null;
    }
}
