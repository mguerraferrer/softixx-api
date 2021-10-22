package softixx.api.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class W2Tag {
	private String message;
	private String position;
	private String cssClassName;
	private String cssStyle;
}