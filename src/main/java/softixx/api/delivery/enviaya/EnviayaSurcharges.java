package softixx.api.delivery.enviaya;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * The breakdown of surcharges that apply to your shipment <br><br>
 * Attributes <br>
 * {@value } <b>name</b> (Integer) - Surcharge name <br>
 * {@value } <b>amount</b> (Integer) - The net amount of the surcharge <br>
 * {@value } <b>vatAmount</b> (Integer) - The vat amount of the surcharge <br>
 * {@value } <b>vatRate</b> (Integer) - The vat rate that applies <br>
 * {@value } <b>totalAmount</b> (Integer) - The total amount of the surcharge
 * @author Maikel Guerra Ferrer
 * @since 1.2.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnviayaSurcharges {
    @JsonProperty("carrier_surcharge_name")
	private String name;
    @JsonProperty("api_net_amount")
    private Double amount;
    @JsonProperty("api_vat_amount")
    private Double vatAmount;
    @JsonProperty("api_vat_rate")
    private Double vatRate;
    @JsonProperty("api_total_amount")
    private Double totalAmount;
    
    protected static String toJsonString(final EnviayaSurcharges obj) throws JsonProcessingException {
    	val mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
    
    protected static EnviayaSurcharges fromJsonString(final String json) throws IOException {
    	val mapper = new ObjectMapper();
        return mapper.readValue(json, EnviayaSurcharges.class);
    }
}