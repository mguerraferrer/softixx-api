package softixx.api.payload;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import softixx.api.util.UCrypto;
import softixx.api.util.UMessage;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OptionDto {
	private String id;
	private String code;
	private String name;
	private String value;
	
	public static List<OptionDto> empty() {
		return new ArrayList<>();
	}
	
	public static OptionDto optDefault() {
		String selectDefaultOption = UMessage.getMessage("select.text.default");
		return OptionDto
				.builder()
				.id("")
				.code(selectDefaultOption)
				.value(selectDefaultOption)
				.build();
	}
	
	public static OptionDto mapper(Object objDto) {
		String id = null;
		String code = null;
		String value = null;
		String name = null;
		
		if (objDto instanceof IOptionDto) {
			val dto = (IOptionDto) objDto;
			id = UCrypto.idEncode(dto.getId());
			code = dto.getCode();
			value = dto.getValue();
			name = dto.getName();
		} else if (objDto instanceof LOptionDto) {
			val dto = (LOptionDto) objDto;
			id = UCrypto.idEncode(dto.getId());
			code = dto.getCode();
			value = dto.getValue();
			name = dto.getName();
		} else if (objDto instanceof BIOptionDto) {
			val dto = (BIOptionDto) objDto;
			id = UCrypto.idEncode(dto.getId());
			code = dto.getCode();
			value = dto.getValue();
			name = dto.getName();
		}
		
		return OptionDto
				.builder()
				.id(id)
				.code(code)
				.name(name)
				.value(value)
				.build();
	}
}