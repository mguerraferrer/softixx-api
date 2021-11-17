package softixx.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.val;

public class UDouble {
	private static final Logger log = LoggerFactory.getLogger(UDouble.class);
	
	public static final DecimalFormat DECIMAL_FORMAT_DEFAULT = new DecimalFormat("#,###.00");
	public static final DecimalFormat DECIMAL_FORMAT_WITHOUT_DECIMALS = new DecimalFormat("#,###");
	public static final RoundingMode HALF_UP = RoundingMode.HALF_UP;
	
	public static Double dValue(String str) {
		try {

			if(UValidator.isNotEmpty(str)) {
				str = UDecimal.decimal(str);
				val d = Double.valueOf(str.replace(",", ""));
				val value = DECIMAL_FORMAT_DEFAULT.format(d);
				return Double.parseDouble(value.replace(",", ""));
			}

		} catch (Exception e) {
			log.error("UDouble#dValue(String) error - {}", e.getMessage());
		}
		return null;
	}
	
	public static Double dValueWithoutDecimals(String str) {
		try {

			if(UValidator.isNotEmpty(str)) {
				str = UDecimal.decimal(str);
				val d = Double.valueOf(str.replace(",", ""));
				val value = DECIMAL_FORMAT_WITHOUT_DECIMALS.format(d);
				return Double.parseDouble(value.replace(",", ""));
			}

		} catch (Exception e) {
			log.error("UDouble#dValueWithoutDecimals error - {}", e.getMessage());
		}
		return null;
	}

	public static Double dValue(Integer i) {
		try {

			if(i != null) {
				Double value = i.doubleValue();
				return value;
			}

		} catch (Exception e) {
			log.error("UDouble#dValue(Integer) error - {}", e.getMessage());
		}
		return null;
	}
	
	public static Double dValue(BigDecimal b) {
		try {

			if(b != null) {
				return b.doubleValue();
			}

		} catch (Exception e) {
			log.error("UDouble#dValue(BigDecimal) error - {}", e.getMessage());
		}
		return null;
	}
	
	public static String dValue(Double d) {
		try {

			if (d != null) {
				var str = DECIMAL_FORMAT_DEFAULT.format(d);
				if(str.startsWith(".")) {
					str = "0".concat(str);
				}
				return str;
			}

		} catch (Exception e) {
			log.error("UDouble#dValue(Double) error - {}", e.getMessage());
		}
		return null;
	}
	
	public static String integerPart(final Double value) {
		try {
			
			if(!UValidator.isNull(value)) {
				val doubleAsString = String.valueOf(value);
				val indexOfDecimal = doubleAsString.indexOf(".");
				
				var dValue = Double.valueOf(doubleAsString.substring(0, indexOfDecimal).replace(",", ""));
				
				DecimalFormat df = new DecimalFormat("#,###");
				val result = df.format(dValue);
				return result;
			}
			
		} catch (Exception e) {
			log.error("UDouble#integerPart error - {}", e.getMessage());
		}
		return null;
	}
	
	public static Double round(final Double value) {
		try {
			
			if(value != null) {
				BigDecimal bd = new BigDecimal(value);
				bd = bd.setScale(2, HALF_UP); //##### 2 decimales
				return dValue(bd);
			}
			
		} catch (Exception e) {
			log.error("UDouble#round error - {}", e.getMessage());
		}
		return null;
	}
	
	public static Double roundCommision(final Double value) {
		try {
			
			if(value != null) {
				//##### Redondeando inicialmente a 2 decimales
				val dVal = round(value);
				
				val strVal = UDouble.dValue(dVal);
				val strSplitted = strVal.split(Pattern.quote("."));
				
				int intVal = UInteger.value(strSplitted[0]); 
				int result = intVal % 10; 
				
				do {
					intVal ++;
					result = intVal % 10;
				} while (result != 0);
				
				return UDouble.dValue(intVal);
			}
			
		} catch (Exception e) {
			log.error("UDouble#roundCommision error - {}", e.getMessage());
		}
		return null;
	}
}