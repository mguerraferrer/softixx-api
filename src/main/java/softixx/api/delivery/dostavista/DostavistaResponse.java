package softixx.api.delivery.dostavista;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

@Data
@NoArgsConstructor
public class DostavistaResponse {
	private static final Logger log = LoggerFactory.getLogger(DostavistaResponse.class);
	
	@JsonProperty("is_successful")
	private Boolean isSuccessful;
	private DostavistaOrder order;
	private List<String> warnings;
	@JsonProperty("parameter_warnings")
	private ParameterWarnings parameterWarnings;
	
	protected static String toJsonString(final DostavistaResponse obj) {
		try {
			
			val mapper = new ObjectMapper();
	        return mapper.writeValueAsString(obj);
			
		} catch (Exception e) {
			log.error("DostavistaResponse#toJsonString error {}", e.getMessage());
		}
    	return null;
    }
    
    protected static DostavistaResponse fromJsonString(final String json) {
    	try {
    		
    		val mapper = new ObjectMapper();
            return mapper.readValue(json, DostavistaResponse.class);
    		
		} catch (Exception e) {
			log.error("DostavistaResponse#fromJsonString error {}", e.getMessage());
		}
    	return null;
    }
    
    @Data
    @NoArgsConstructor
    protected static class ParameterWarnings {
        private List<ParameterWarningsPoint> points;
        
        @Data
        @NoArgsConstructor
        protected static class ParameterWarningsPoint {
        	@JsonProperty("contact_person")
            private FluffyContactPerson contactPerson;
            
            @Data
            @NoArgsConstructor
            protected static class FluffyContactPerson {
                private List<String> name;
                private List<String> phone;
            }
        }
    }
}
