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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnviayaDirection {
	@JsonProperty("full_name")
    private String fullName;
    private String company;
    @JsonProperty("direction_1")
    private String direction1;
    @JsonProperty("direction_2")
    private String direction2;
    private String neighborhood;
    private String district;
    @JsonProperty("postal_code")
    private String postalCode;
    private String city;
    @JsonProperty("state_code")
    private String stateCode;
    @JsonProperty("country_code")
    private String countryCode;
    private String phone;
    private String email;
    
    public static String toJsonString(final EnviayaDirection obj) throws JsonProcessingException {
    	val mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
    
    public static EnviayaDirection fromJsonString(final String json) throws IOException {
    	val mapper = new ObjectMapper();
        return mapper.readValue(json, EnviayaDirection.class);
    }
}