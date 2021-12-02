package softixx.api.custom;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ErrorMessage implements Serializable {
	private static final long serialVersionUID = 2710587557120603424L;
	
	private String field;
	private String error;
	private String alternativeError;
	
	public ErrorMessage(String error) {
		this.error = error;
	}
}