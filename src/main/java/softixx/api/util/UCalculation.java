package softixx.api.util;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import lombok.val;

public class UCalculation {	
	
	private static final Logger log = LoggerFactory.getLogger(UCalculation.class);
	
	public static Double percentage(final Double subtotal, final Integer percentage) {
		try {
			
			var value = UDouble.dValue(UInteger.value(percentage));
			value = subtotal * value / 100;
			return value;
			
		} catch (Exception e) {
			log.error("UCalculation#percentage error - {}", e.getMessage());
		}
		return null;
	}
	
	public static BigDecimal percentage(final BigDecimal subtotal, final Integer percentage) {
		try {
			
			var value = UBigDecimal.bValue(percentage);
			value = subtotal.multiply(value).divide(new BigDecimal(100));
			return value;
			
		} catch (Exception e) {
			log.error("UCalculation#percentage error - {}", e.getMessage());
		}
		return null;
	}
	
	public static Double percentage(final Double subtotal, final Double percentage) {
		try {
			
			var value = subtotal * percentage / 100;
			return value;
			
		} catch (Exception e) {
			log.error("UCalculation#percentage error - {}", e.getMessage());
		}
		return null;
	}
	
	public static Double total(final Double subTotal, @Nullable Double taxOrShipping) {
		try {
			
			if (taxOrShipping == null) {
				taxOrShipping = 0d;
			}
			
			//##### TOTAL = SUBTOTAL + (IVA o ENVIO)
			val total = subTotal + taxOrShipping;
			return total;
			
		} catch (Exception e) {
			log.error("UCalculation#total error - {}", e.getMessage());
		}
		return null;
	}
	
	public static Double total(final Double subTotal, @Nullable Double taxOrShipping, @Nullable Double discount) {
		try {
			
			if (discount == null) {
				discount = 0d;
			}
			
			if (taxOrShipping == null) {
				taxOrShipping = 0d;
			}
			
			//##### TOTAL = SUBTOTAL + (IVA o ENVIO) – DESCUENTO
			val total = subTotal + taxOrShipping - discount;
			return total;
			
		} catch (Exception e) {
			log.error("UCalculation#total error - {}", e.getMessage());
		}
		return null;
	}
	
	public static Double total(final Double subTotal, @Nullable Double discount, @Nullable Double shiping, @Nullable Double tax) {
		try {
			
			if (discount == null) {
				discount = 0d;
			}
			
			if (shiping == null) {
				shiping = 0d;
			}
			
			if (tax == null) {
				tax = 0d;
			}
			
			//##### TOTAL = SUBTOTAL + ENVIO + IVA – DESCUENTO
			val total = subTotal + shiping + tax - discount;
			return total;
			
		} catch (Exception e) {
			log.error("UCalculation#total error - {}", e.getMessage());
		}
		return null;
	}
	
	public static BigDecimal total(final BigDecimal subTotal, BigDecimal tax) {
		try {
			
			String valueNull = null;
			
			if (tax == null) {
				tax = UBigDecimal.bValue(valueNull);
			}
			
			//##### TOTAL = SUBTOTAL + IVA
			val total = subTotal.add(tax);
			return total;
			
		} catch (Exception e) {
			log.error("UCalculation#total error - {}", e.getMessage());
		}
		return null;
	}
	
	public static BigDecimal total(final BigDecimal subTotal, BigDecimal discount, BigDecimal tax) {
		try {
			
			String valueNull = null;
			
			if (tax == null) {
				tax = UBigDecimal.bValue(valueNull);
			}
			
			if (discount == null) {
				discount = UBigDecimal.bValue(valueNull);
			}
			
			//##### TOTAL = SUBTOTAL + IVA – DESCUENTO
			val total = subTotal.add(tax).subtract(discount);
			return total;
			
		} catch (Exception e) {
			log.error("UCalculation#total error - {}", e.getMessage());
		}
		return null;
	}
	
	public static Integer penniesInt(final Double total) {
		if (total != null) {
			val totalInPennies = UInteger.value(total * 100);
			return totalInPennies;
			
		}
		return 0;
	}
	
	public static Long penniesLong(final Double total) {
		if (total != null) {
			val totalInPennies = UInteger.value(total * 100);
			return ULong.value(totalInPennies);
			
		}
		return 0l;
	}
}