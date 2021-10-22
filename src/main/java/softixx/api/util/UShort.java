package softixx.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UShort {
	private static final Logger log = LoggerFactory.getLogger(UShort.class);
	
	public static Short value(final String str) {
		try {

			if (UValidator.isNotEmpty(str)) {
				return Short.parseShort(str);
			}

		} catch (Exception e) {
			log.error("UShort#value(String) error {}", e.getMessage());		
		}
		return null;
	}
	
	public static String value(final Short sh) {
		try {

			if (UValidator.isNull(sh)) {
				return Short.toString(sh);
			}

		} catch (Exception e) {
			log.error("UShort#value(Short) error {}", e.getMessage());		
		}
		return null;
	}
}