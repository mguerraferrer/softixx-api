package softixx.api.util;

import java.math.BigInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UInteger {
	private UInteger() {
		throw new IllegalStateException("Utility class");
	}

	public static Integer value(final Object obj) {
		if (UValidator.isNotEmpty(obj)) {
			return value(obj.toString());
		}
		return null;
	}
	
	public static Integer value(final String integerStr) {
		try {

			if (UValidator.isNotEmpty(integerStr)) {
				return Integer.valueOf(integerStr);
			}

		} catch (Exception e) {
			log.error("UInteger#value(String) error {}", e.getMessage());
		}
		return null;
	}

	public static String value(final Integer number) {
		try {

			if (number != null) {
				return number.toString();
			}

		} catch (Exception e) {
			log.error("UInteger#value(Integer) error {}", e.getMessage());
		}
		return null;
	}
	
	public static Integer value(final Double dValue) {
		try {

			if (dValue != null) {
				return dValue.intValue();
			}

		} catch (Exception e) {
			log.error("UInteger#value(Double) error {}", e.getMessage());
		}
		return null;
	}
	
	public static Integer value(final Long lValue) {
		try {

			if (lValue != null) {
				return lValue.intValue();
			}

		} catch (Exception e) {
			log.error("UInteger#value(Long) error {}", e.getMessage());
		}
		return null;
	}

	public static Integer value(final BigInteger bi) {
		try {

			if (bi != null) {
				return bi.intValue();
			}

		} catch (Exception e) {
			log.error("UInteger#value(BigInteger) error {}", e.getMessage());
		}
		return null;
	}

	public static Integer value(final Number number) {
		try {

			if (number != null) {
				return number.intValue();
			}

		} catch (Exception e) {
			log.error("UInteger#value(Number) error {}", e.getMessage());
		}
		return null;
	}
}