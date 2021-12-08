package softixx.api.json;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JSource {
	private String id;
	private String name;
	private String code;
	private String value;
	
	public JSource(String value) {
		this.value = value;
	}
}