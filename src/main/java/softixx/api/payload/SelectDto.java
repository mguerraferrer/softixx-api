package softixx.api.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectDto {

	private String id;
	private String code;
	private String value;
	
}