package softixx.api.json;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class JGenericResponse {
	JResponse response;
	
	public static JGenericResponse response(JResponse response) {
		val gr = new JGenericResponse();
		gr.setResponse(response);
		return gr;
	}
}