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
public class EnviayaAccount {
	private static final Logger log = LoggerFactory.getLogger(EnviayaAccount.class);
	
	@JsonProperty("enviaya_account")
	private String enviayaAccount;
	@JsonProperty("carrier_account")
	private String carrierAccount;
	
	protected static String toJsonString(final EnviayaAccount obj) {
		try {
			
			val mapper = new ObjectMapper();
	        return mapper.writeValueAsString(obj);
			
		} catch (Exception e) {
			log.error("EnviayaAccount toJsonString error - {}", e.getMessage());
		}
    	return null;
    }
    
    protected static EnviayaAccount fromJsonString(final String json) {
    	try {
    		
    		val mapper = new ObjectMapper();
            return mapper.readValue(json, EnviayaAccount.class);
    		
		} catch (Exception e) {
			log.error("EnviayaAccount fromJsonString error - {}", e.getMessage());
		}
    	return null;
    }
}
