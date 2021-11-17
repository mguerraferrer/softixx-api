package softixx.api.delivery.enviaya;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.val;

@Data
@NoArgsConstructor
public class EnviayaCatalogue {
	private static final Logger log = LoggerFactory.getLogger(EnviayaCatalogue.class);
	
	@Singular
	private List<Map<String, String>> carriers;
	@Singular
	private List<List<Map<String, String>>> services;
	@JsonProperty("shipment_statuses")
	@Singular
	private List<Map<String, String>> shipmentStatuses;
	@JsonProperty("pickup_statuses")
	@Singular
	private List<Map<String, String>> pickupStatuses;
	
	protected static String toJsonString(final EnviayaCatalogue obj) {
		try {
			
			val mapper = new ObjectMapper();
	        return mapper.writeValueAsString(obj);
			
		} catch (Exception e) {
			log.error("EnviayaCatalogue toJsonString error - {}", e.getMessage());
		}
    	return null;
    }
    
    protected static EnviayaCatalogue fromJsonString(final String json) {
    	try {
    		
    		val mapper = new ObjectMapper();
            return mapper.readValue(json, EnviayaCatalogue.class);
    		
		} catch (Exception e) {
			log.error("EnviayaCatalogue fromJsonString error - {}", e.getMessage());
		}
    	return null;
    }
    
    public enum CatalogueType {
    	CARRIERS, SERVICES, SHIPMENT_STATUSES, PICKUP_STATUSES
    }
}