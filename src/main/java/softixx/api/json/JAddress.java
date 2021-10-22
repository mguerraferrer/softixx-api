package softixx.api.json;

import lombok.Data;
import lombok.NoArgsConstructor;
import softixx.api.wrapper.WAddress;

@Data
@NoArgsConstructor
public class JAddress {
	private WAddress address;
	private JResponse response;
	
	@Data
	@NoArgsConstructor
	public static class AddressData {
		private String state;
		private String town;
		private String colony;
		private String zipCode;
	}
}