package softixx.api.delivery.enviaya;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnviayaRating {
    @JsonProperty("enviaya_account")
    private String enviayaAccount;
    @JsonProperty("carrier_account")
    private String carrierAccount;
    @JsonProperty("api_key")
    private String apiKey;
    private EnviayaShipment shipment;
    @JsonProperty("origin_direction")
    private EnviayaDirection originDirection;
    @JsonProperty("destination_direction")
    private EnviayaDirection destinationDirection;
    @JsonProperty("insured_value")
    private String insuredValue;
    @JsonProperty("insured_value_currency")
    private String insuredValueCurrency;
    @JsonProperty("order_total_amount")
    private long orderTotalAmount;
    private String currency;
    @Default
    @JsonProperty("intelligent_filtering")
    private boolean intelligentFiltering = false;
    
    protected static String toJsonString(final EnviayaRating obj) throws JsonProcessingException {
    	val mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
    
    protected static EnviayaRating fromJsonString(final String json) throws IOException {
    	val mapper = new ObjectMapper();
        return mapper.readValue(json, EnviayaRating.class);
    }
}