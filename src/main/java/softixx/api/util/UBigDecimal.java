package softixx.api.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

import lombok.val;

public class UBigDecimal {	
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###");
	private static final DecimalFormat DECIMAL_FORMAT_01 = new DecimalFormat("#,###.0");
	private static final DecimalFormat DECIMAL_FORMAT_DEFAULT = new DecimalFormat("#,###.00");
	private static final DecimalFormat DECIMAL_FORMAT_03 = new DecimalFormat("#,###.000");
	private static final DecimalFormat DECIMAL_FORMAT_04 = new DecimalFormat("#,###.0000");
	private static final DecimalFormat DECIMAL_FORMAT_05 = new DecimalFormat("#,###.00000");
	private static final DecimalFormat DECIMAL_FORMAT_06 = new DecimalFormat("#,###.000000");
	
	public static String strValue(BigDecimal b) {
		String str = null;
		try {

			if (b != null) {
				str = String.valueOf(b);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String strValue(String str, Integer decimal) {
		try {

			str = UDecimal.decimal(str);			
			val df = decimalFormat(decimal);

			var value = df.format(BigDecimal.ZERO);
			if (str == null || str.equals("")) {
				return value;
			}

			val b = new BigDecimal(str.replaceAll(",", ""));
			value = df.format(b);
			return value;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BigDecimal bValue(String str) {
		try {
			
			str = UDecimal.decimal(str);
			val bigDecimal = new BigDecimal(str.replaceAll(",", ""));
			String value = DECIMAL_FORMAT_DEFAULT.format(bigDecimal);
			return new BigDecimal(value.replaceAll(",", ""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return BigDecimal.ZERO;
	}

	public static BigDecimal bValue(String str, Integer decimal) {
		try {

			str = UDecimal.decimal(str);			
			val df = decimalFormat(decimal);

			val b = new BigDecimal(str.replaceAll(",", ""));
			val value = df.format(b);
			return new BigDecimal(value.replaceAll(",", ""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BigDecimal bValue(Integer decimal) {
		try {

			val str = "0";
			val df = decimalFormat(decimal);

			BigDecimal bigDecimal = new BigDecimal(str.replaceAll(",", ""));
			String value = df.format(bigDecimal);
			return new BigDecimal(value.replaceAll(",", ""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BigDecimal valueForce(String str) {
		try {

			if (str == null) {
				return null;
			}

			return new BigDecimal(str.replaceAll(",", ""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BigDecimal valueForce(String str, Integer decimal) {
		try {

			if (str == null) {
				return null;
			}

			str = UDecimal.decimal(str);
			val df = decimalFormat(decimal);

			if (df != null) {
				BigDecimal bigDecimal = new BigDecimal(str.replaceAll(",", ""));
				String value = df.format(bigDecimal);
				return new BigDecimal(value.replaceAll(",", ""));
			} else
				return new BigDecimal(str.replaceAll(",", ""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BigDecimal bValueSix(String str) {
		try {

			str = UDecimal.decimal(str);
			BigDecimal bigDecimal = new BigDecimal(str.replaceAll(",", ""));
			String value = DECIMAL_FORMAT_06.format(bigDecimal);
			return new BigDecimal(value.replaceAll(",", ""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BigDecimal bValueSixForce(String str) {
		try {

			if (str == null) {
				return null;
			}

			return bValueSix(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String integerPart(final BigDecimal value) {
		try {
			
			if(UValidator.isNull(value)) {
				return null;
			}
			
			DecimalFormat df = new DecimalFormat("#,###");
			val bgValue = df.format(value);
			return bgValue;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BigDecimal value(BigInteger bi) {
		try {

			if (bi != null) {
				return new BigDecimal(bi);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BigDecimal value(Number number) {
		try {

			if (number != null) {
				return new BigDecimal(number.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static DecimalFormat decimalFormat(Integer decimal) {
		DecimalFormat df = null;
		if (decimal == null || decimal == 0) {
			df = DECIMAL_FORMAT;
		} else if (decimal == 1) {
			df = DECIMAL_FORMAT_01;
		} else if (decimal == 2) {
			df = DECIMAL_FORMAT_DEFAULT;
		} else if (decimal == 3) {
			df = DECIMAL_FORMAT_03;
		} else if (decimal == 4) {
			df = DECIMAL_FORMAT_04;
		} else if (decimal == 5) {
			df = DECIMAL_FORMAT_05;
		} else if (decimal == 6) {
			df = DECIMAL_FORMAT_06;
		}
		return df;
	}
}