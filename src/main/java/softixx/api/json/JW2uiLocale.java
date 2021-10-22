package softixx.api.json;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JW2uiLocale {
	private String locale;
	private String dateFormat;
	private String timeFormat;
	private String currency;
	private String currencyPrefix;
	private String currencySuffix;
	private String groupSymbol;
	private String sfloat;
	private String[] shortmonths;
	private String[] fullmonths;
	private String[] shortdays;
	private String[] fulldays;
	@Singular
	private Map<String, String> phrases;
	
}