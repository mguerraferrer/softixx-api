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
	public static final String SELECT_DEFAULT_OPTION = "<option value=\"\">"
			+ UMessage.getMessage("select.text.default") + "</option>";

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
		return JSelect
				.builder()
				.optionsSource(SELECT_DEFAULT_OPTION)
				.build();
	}

	public static JSelect optionsSource(final String options) {
		return JSelect
				.builder()
				.optionsSource(options)
				.build();
	}

	public static JSelect optionsSource(final String options, List<AdditionalData> additionalData) {
		return JSelect
				.builder()
				.optionsSource(options)
				.clearAdditionalData()
				.additionalData(additionalData)
				.build();
	}
}