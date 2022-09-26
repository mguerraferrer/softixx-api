package softixx.api.util;

import java.math.BigInteger;

public class UBigInteger {

	public static BigInteger value(Integer number) {
		try {

			if (number != null) {
				return BigInteger.valueOf(number.intValue());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BigInteger bigInteger(String str) {
		try {
			
			//JDK 11
			//if (str != null && !str.isBlank() && !str.isEmpty()) {
			if (str != null && !str.isEmpty()) {
				return new BigInteger(str);
			}			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
