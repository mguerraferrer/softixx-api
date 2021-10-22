package softixx.api.delivery.enviaya;

import java.io.IOException;
import java.util.List;

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
public class EnviayaRate {
    @JsonProperty("rate_id")
    private String rateId;
    @JsonProperty("shipment_id")
    private String shipmentId;
    @JsonProperty("dynamic_service_name")
    private String dynamicServiceName;
    private String date;
    private String carrier;
    @JsonProperty("carrier_service_name")
    private String carrierServiceName;
    @JsonProperty("carrier_service_code")
    private String carrierServiceCode;
    @JsonProperty("carrier_logo_url")
    private String carrierLogoURL;
    @JsonProperty("carrier_logo_url_svg")
    private String carrierLogoURLSVG;
    @JsonProperty("estimated_delivery")
    private String estimatedDelivery;
    @JsonProperty("est_transit_time_hours")
    private Long estTransitTimeHours;
    @JsonProperty("net_shipping_amount")
    private Double netShippingAmount;
    @JsonProperty("net_surcharges_amount")
    private Double netSurchargesAmount;
    @JsonProperty("net_total_amount")
    private Double netTotalAmount;
    @JsonProperty("vat_amount")
    private Double vatAmount;
    @JsonProperty("vat_rate")
    private Long vatRate;
    @JsonProperty("total_amount")
    private Double totalAmount;
    private String currency;
    @JsonProperty("list_total_amount")
    private Double listTotalAmount;
    @JsonProperty("list_total_amount_currency")
    private String listTotalAmountCurrency;
    @JsonProperty("subsidy_net_amount")
    private Double subsidyNetAmount;
    @JsonProperty("service_terms")
    private String serviceTerms;
    @JsonProperty("surcharges_break_down")
    @Singular
    private List<EnviayaSurcharges> surcharges;
    @JsonProperty("enviaya_service_name")
    private String enviayaServiceName;
    @JsonProperty("enviaya_service_code")
    private String enviayaServiceCode;
    @JsonProperty("additional_configuration")
    private Object additionalConfiguration;
    
    protected static String toJsonString(final EnviayaRating obj) throws JsonProcessingException {
    	val mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
    
    protected static EnviayaRating fromJsonString(final String json) throws IOException {
    	val mapper = new ObjectMapper();
        return mapper.readValue(json, EnviayaRating.class);
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnviayaRateResponse {
    	@Singular
    	private List<EnviayaRate> ups;
    	@Singular(value = "redpack")
    	private List<EnviayaRate> redpack;
    	@Singular(value = "estafeta")
    	private List<EnviayaRate> estafeta;
    	@Singular(value = "fedex")
    	private List<EnviayaRate> fedex;
    	@Singular(value = "paquetexpress")
    	private List<EnviayaRate> paquetexpress;
    	@Singular(value = "four72")
    	private List<EnviayaRate> four72;
    	@Singular(value = "ivoy")
    	private List<EnviayaRate> ivoy;
    	@Singular(value = "minutos")
    	private List<EnviayaRate> minutos;
    	@Singular(value = "tresguerras")
    	private List<EnviayaRate> tresguerras;
    	@Singular(value = "ampm")
    	private List<EnviayaRate> ampm;
    	@Singular(value = "sendex")
    	private List<EnviayaRate> sendex;
    	@Singular(value = "warning")
    	private List<String> warning;
    	
    	protected static String toJsonString(final EnviayaRateResponse obj) throws JsonProcessingException {
        	val mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        }
        
        protected static EnviayaRateResponse fromJsonString(final String json) throws IOException {
        	val mapper = new ObjectMapper();
            return mapper.readValue(json, EnviayaRateResponse.class);
        }
    }
    
    
}
