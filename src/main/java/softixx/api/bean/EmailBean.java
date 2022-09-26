package softixx.api.bean;

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
public class EmailBean {
	
	private String recipient;
	@Singular("recipients")
	private List<String> recipients;
	private String subject;
	private Map<String, String> ctxMap;
	private String ctx;
	
	public static EmailBean populate(final String recipient, final String subject, final Map<String, String> ctxMap) {
		val bean = EmailBean
					.builder()
			    	.recipient(recipient)
			    	.subject(subject)
			    	.ctxMap(ctxMap)
			    	.ctx(ctx(ctxMap))
			    	.clearRecipients()
			    	.recipients(Arrays.asList(recipient))
					.build();
		return bean;
	}
	
	public static String ctx(Map<String, String> ctxMap) {
		if (ctxMap == null || ctxMap.isEmpty()) {
			return null;
		}
		
		String ctx = null;
		for (val entry : ctxMap.entrySet()) {
			val key = entry.getKey();
			val value = entry.getValue();
			val kv = value != null ? key.concat("=").concat(value) : key.concat("=");
			
			if (ctx == null) {
				ctx = kv;
			} else {
				ctx = ctx.concat("&").concat(kv);
			}
		}
		return ctx;
	}

}