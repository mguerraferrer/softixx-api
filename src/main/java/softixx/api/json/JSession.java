package softixx.api.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JSession {
	private String sessionId;
	private String sessionUserId;
	private String sessionDate;
	private String sessionTime;
	private String sessionDateTime;
	@Default
	private boolean sessionActive = false;
	@Default
	private boolean sessionError = false;
}