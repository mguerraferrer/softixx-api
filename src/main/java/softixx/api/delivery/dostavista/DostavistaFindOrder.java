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
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class DostavistaFindOrder {
	private static final Logger log = LoggerFactory.getLogger(DostavistaFindOrder.class);
	
	@JsonProperty("order_id")
	private Integer orderId;
	private String status;
	@Default
	private Integer offset = 0;
	@Default
	private Integer count = 10;
	
	protected static String toJsonString(final DostavistaFindOrder obj) {
		try {
			
			val mapper = new ObjectMapper();
	        return mapper.writeValueAsString(obj);
			
		} catch (Exception e) {
			log.error("DostavistaFindOrder#toJsonString error - {}", e.getMessage());
		}
    	return null;
    }
    
    protected static DostavistaFindOrder fromJsonString(final String json) {
    	try {
    		
    		val mapper = new ObjectMapper();
            return mapper.readValue(json, DostavistaFindOrder.class);
    		
		} catch (Exception e) {
			log.error("DostavistaFindOrder#fromJsonString error - {}", e.getMessage());
		}
    	return null;
    }
    
    @Data
    @NoArgsConstructor
    public static class DostavistaFindOrderResponse {
    	@JsonProperty("is_successful")
    	private Boolean isSuccessful;
    	private List<DostavistaOrder> orders;
    	@JsonProperty("orders_count")
    	private Integer ordersCount;
    }
}
