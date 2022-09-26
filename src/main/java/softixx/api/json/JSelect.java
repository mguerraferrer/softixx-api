package softixx.api.json;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import softixx.api.util.UMessage;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JSelect {
	private static final String OPTION_START = "<option value=\"";
	private static final String OPTION_END = "</option>";
	
	public static final String SELECT_OPTION_EMPTY = "-";
	public static final String SELECT_OPTION_DEFAULT = "<option value=\"\">"
			+ UMessage.getMessage("select.text.default") + OPTION_END;

	private Option option;
	@Singular("options")
	private List<Option> options;
	private String optionsSource;
	private String totalOptions;
	@Singular("additionalData")
	private List<AdditionalData> additionalData;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Option {
		private String optionId;
		private String optionCode;
		private String optionValue;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AdditionalData {
		private String keyId;
		private String keyCode;
		private String keyValue;
		private String opt1Id;
		private String opt1Code;
		private String opt1Value;
		private String opt2Id;
		private String opt2Code;
		private String opt2Value;
		private String opt3Id;
		private String opt3Code;
		private String opt3Value;
	}

	public static JSelect empty() {
		return JSelect.builder().optionsSource(SELECT_OPTION_DEFAULT).build();
	}

	public static JSelect optionsSource(final String options) {
		return JSelect.builder().optionsSource(options).build();
	}

	public static JSelect optionsSource(final String options, List<AdditionalData> additionalData) {
		return JSelect.builder().optionsSource(options).clearAdditionalData().additionalData(additionalData).build();
	}

	public static String defaultOption() {
		return SELECT_OPTION_DEFAULT;
	}
	
	public static String option(String id, String value) {
		return OPTION_START + id + "\">" + value + OPTION_END;
	}
	
	public static String option(String id, String value, boolean isSelected) {
		if (isSelected) {
			return OPTION_START + id + "\" selected=\"selected\">" + value + OPTION_END;
		}
		return OPTION_START + id + "\">" + value + OPTION_END;
	}
	
	public static String option(String value) {
		return OPTION_START + value + "\">" + value + OPTION_END;
	}
}