package softixx.api.util;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ULong {
	private static final Logger log = LoggerFactory.getLogger(ULong.class);
	
	public static Long value(final String str) {
		try {

			if (UValidator.isNotEmpty(str)) {
				return Long.valueOf(str);
			}

		} catch (Exception e) {
			log.error("ULong#value(String) error {}", e.getMessage());
		}
		return null;
	}
	
	public static Long value(final Integer intVal) {
		try {

			if (intVal != null) {
				return Long.valueOf(intVal);
			}

		} catch (Exception e) {
			log.error("ULong#value(Integer) error {}", e.getMessage());
		}
		return null;
	}

	public static String value(final Long l) {
		try {

			if (l != null) {
				return Long.toString(l);
			}

		} catch (Exception e) {
			log.error("ULong#value(Long) error {}", e.getMessage());
		}
		return null;
	}

	public static Long value(BigInteger bi) {
		try {

			if (bi != null) {
				return bi.longValue();
			}

		} catch (Exception e) {
			log.error("ULong#value(BigInteger) error {}", e.getMessage());
		}
		return null;
	}

	public static Long value(final Number number) {
		try {

			if (number != null) {
				return number.longValue();
			}

		} catch (Exception e) {
			log.error("ULong#value(Number) error {}", e.getMessage());
		}
		return null;
	}
}