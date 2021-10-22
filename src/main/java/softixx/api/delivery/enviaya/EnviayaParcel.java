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
 * The parcel object defines the dimensions and the weight of the parcels or
 * document you are sending. A shipment can contain more than just one parcel.
 * If all parcels you are sending do have the same dimensions and weight, you
 * can use the field quantity so specify how many parcels you are sending. If
 * dimension or weight differ, you can send several parcel objects in your
 * request. <br>
 * <b>Please note:</b> It is usually cheaper to send one shipment with several parcels
 * instead of sending one separate shipment for each parcel you are sending. (Of
 * course, this only applies as long as all your parcels are sent to the same
 * destination.) <br><br>
 * 
 * Attributes <br>
 * {@value } <b>quantity</b> (Integer) - The quantity of parcels with the same dimensions and weight <b><i>*Required</i></b> <br>
 * {@value } <b>weight</b> (Double) - The weight of the parcel <b><i>*Required</i></b> <br>
 * {@value } <b>weightUnit</b> (String) - The weight unit used. Has to be either <i>'kg'</i> and <i>'lbs'</i> <b><i>*Required</i></b> <br>
 * {@value } <b>length</b> (Double) - The length of the parcel <b><i>*Required</i></b> <br>
 * {@value } <b>height</b> (Double) - The height of the parcel <b><i>*Required</i></b> <br>
 * {@value } <b>width</b> (Double) - The width of the parcel <b><i>*Required</i></b> <br>
 * {@value } <b>dimensionUnit</b> (String) - The dimension unit used. Has to be either <i>'cm'</i> and <i>'in'</i> <b><i>*Required</i></b>
 * @author Maikel Guerra Ferrer
 * @since 1.2.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnviayaParcel {
	private Integer quantity;
	private Double weight;
	@JsonProperty("weight_unit")
	private String weightUnit;
	private Double length;
	private Double height;
	private Double width;
	@JsonProperty("dimension_unit")
	private String dimensionUnit;
	
	public static String toJsonString(final EnviayaParcel obj) throws JsonProcessingException {
    	val mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
    
    public static EnviayaParcel fromJsonString(final String json) throws IOException {
    	val mapper = new ObjectMapper();
        return mapper.readValue(json, EnviayaParcel.class);
    }
}