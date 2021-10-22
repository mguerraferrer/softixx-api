package softixx.api.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
public class UMessage {
	@Autowired
	private MessageSource messageSource;

	private static MessageSourceAccessor accessor;

	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
	}
	
	public static String getMessage(final String key) {
		return getMessage(key, null);
	}
	
	public static String getMessage(final String key, @Nullable Object[] params) {
		if(UValidator.isNotEmpty(key)) {
			return accessor.getMessage(key, params, LocaleContextHolder.getLocale());
		}
		return null;
	}
	
	public static String getMessage(final CustomMessage customMessage) {
		if(UValidator.isNotNull(customMessage)) {
			return accessor.getMessage(customMessage.getKey(), customMessage.getParams(), LocaleContextHolder.getLocale());
		}
		return null;
	}
	
	@Data
	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	public static class CustomMessage {
		private String key;
		private Object[] params;
	}
	
}