package softixx.api.wrapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.val;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WQueuedEmail {
	
	private Object user;
	private Object notificationType;
	private String from;	
	private String recipient;
	@Singular("recipients")
	private List<String> recipients;
	private String copyTo;
	private String subject;
	private String body;
	private Map<String, String> ctxMap;
	private String ctx;
	@Singular("objList")
	private List<Object> objList;
	
	public static WQueuedEmail populate(final Object user, final String notificationType, final String recipient, final String subject, final String body, final Map<String, String> ctxMap) {
		return WQueuedEmail
				.builder()
				.user(user)
		    	.notificationType(notificationType)
		    	.recipient(recipient)
		    	.subject(subject)
		    	.body(body)
		    	.ctxMap(ctxMap)
		    	.ctx(ctx(ctxMap))
		    	.clearRecipients()
		    	.recipients(Arrays.asList(recipient))
				.build();
	}
	
	public static WQueuedEmail populate(final String recipient, final String subject, final String body, final Map<String, String> ctxMap) {
		return WQueuedEmail
				.builder()
		    	.recipient(recipient)
		    	.subject(subject)
		    	.body(body)
		    	.ctxMap(ctxMap)
		    	.ctx(ctx(ctxMap))
		    	.clearRecipients()
		    	.recipients(Arrays.asList(recipient))
				.build();
	}
	
	public static String ctx(Map<String, String> ctxMap) {
		if(ctxMap == null || ctxMap.isEmpty()) {
			return null;
		}
		
		String ctx = null;
		for (val entry : ctxMap.entrySet()) {
			val key = entry.getKey();
			val value = entry.getValue();
			val kv = value != null ? key.concat("=").concat(value) : key.concat("=");
			
			if(ctx == null) {
				ctx = kv;
			} else {
				ctx = ctx.concat("&").concat(kv);
			}
		}
		return ctx;
	}

}