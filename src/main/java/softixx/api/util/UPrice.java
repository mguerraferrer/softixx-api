package softixx.api.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class UPrice {

	private static final int MIN_DECIMALS = 2;
	private static final int MAX_DECIMALS = 6;
	
	public static String price(Double price, boolean comma) {
		return price(price, MIN_DECIMALS, MAX_DECIMALS, comma);
	}
	
	public static String price(Double price, Integer decimals) {
		return price(price, decimals, decimals, true);
	}
	
	public static String price(Double price) {
		return price(price, MIN_DECIMALS, MIN_DECIMALS, true);
	}
	
	private static String price(Double price, Integer minDecimals, Integer maxDecimals, boolean comma) {
		try {
			
			if (!UValidator.isNull(price)) {
				DecimalFormatSymbols dfs = new DecimalFormatSymbols();
				dfs.setDecimalSeparator('.');
				dfs.setGroupingSeparator(',');

				String formatedValue = "#";
				if (comma) {
					formatedValue = "###,###,###,###";
				}
				
				if (maxDecimals > 0) {
					formatedValue += ".";
				}
				
				for (int i = 0; i < maxDecimals; i++) {
					formatedValue += "#";
				}

				NumberFormat formatter = new DecimalFormat(formatedValue, dfs);
				String result = formatter.format(price);

				if (minDecimals > 0) {
					if (result.indexOf(".") == -1) {
						result += ".";
					}
					
					String aux = result.substring(result.indexOf("."));
					if (aux.length() <= minDecimals) {
						for (int i = aux.length() - 1; i < minDecimals; i++) {
							result += "0";
						}
					}
				}

				return result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}