package softixx.api.custom;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import softixx.api.util.UValidator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RestErrorMessage implements Serializable {
	private static final long serialVersionUID = -3014929427171109156L;
	
	private String field;
	private String message;
	private String error;
	private Integer status;
	private String path;
	
	public RestErrorMessage(ErrorMessage em) {
		if (em != null) {
			this.field = em.getField();
			this.message = em.getError();
		}
	}
	
	public RestErrorMessage(String field, String message) {
		this.field = field;
		this.message = message;
	}
	
	public RestErrorMessage(FieldError fe) {
		if (fe != null) {
			this.field = fe.getField();
			this.message = fe.getDefaultMessage();
			
			val args = fe.getArguments();
			if (UValidator.isNotEmptyArgs(args) && UValidator.isNotEmptyArgs(args[1])) {
				var showOnlyApiField = false;
				
				val size = args.length;
				showOnlyApiField = Boolean.valueOf(args[size - 1].toString());
				/*if (size == 4 && UValidator.isNotEmptyArgs(args[3])) {
					showOnlyApiField = Boolean.valueOf(args[3].toString());
				} else if (size == 5 && UValidator.isNotEmptyArgs(args[4])) {
					showOnlyApiField = Boolean.valueOf(args[4].toString());
				} else if (size == 6 && UValidator.isNotEmptyArgs(args[5])) {
					showOnlyApiField = Boolean.valueOf(args[5].toString());
				}*/
				
				if (showOnlyApiField) {
					var apiField = args[1].toString();
					
					try {
						
						if (this.field != null && this.field.contains(".")) {
							val objName = this.field.split(Pattern.quote("."));
							apiField = objName[0] + "." + apiField;
						}
						
					} catch (Exception ignore) {}
					
					this.field = apiField; 
				}
			}
		}
	}
}