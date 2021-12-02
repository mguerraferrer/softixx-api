package softixx.api.custom;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import lombok.Singular;
import lombok.val;
import softixx.api.util.UValidator;

/**
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
public class RestResponseBuilder implements Serializable {
	private static final long serialVersionUID = -3993524033591150918L;
	
	private Integer status;
	private String error;
	@Singular
	private List<RestErrorMessage> errors;
	private String message;
	private String path;

	public RestResponseBuilder status(int status) {
		this.status = status;
		return this;
	}

	public RestResponseBuilder status(HttpStatus status) {
		this.status = status.value();
		
		if (status.isError()) {
			this.error = status.getReasonPhrase();
		}
		
		return this;
	}

	public RestResponseBuilder error(String error) {
		this.error = error;
		return this;
	}
	
	public RestResponseBuilder errors(List<RestErrorMessage> errors) {
		this.errors = errors;
		return this;
	}

	public RestResponseBuilder exception(ResponseStatusException exception) {
		val httpStatus = exception.getStatus();
		this.status = httpStatus.value();

		if (!Objects.requireNonNull(exception.getReason()).isBlank()) {
			this.message = exception.getReason();
		}

		if (httpStatus.isError()) {
			this.error = httpStatus.getReasonPhrase();
		}

		return this;
	}

	public RestResponseBuilder message(String message) {
		this.message = message;
		return this;
	}

	public RestResponseBuilder path(String path) {
		this.path = path;
		return this;
	}

	public RestResponse build() {
		val errorMessage = new RestErrorMessage();
		errorMessage.setStatus(status);
		errorMessage.setError(error);
		errorMessage.setMessage(message);
		errorMessage.setPath(path);
		
		if (UValidator.isEmpty(this.errors)) {
			this.errors = List.of(errorMessage); 
		}
		
		val response = new RestResponse();
		response.setErrors(this.errors);

		return response;
	}

	public ResponseEntity<RestResponse> entity() {
		return ResponseEntity.status(status).headers(HttpHeaders.EMPTY).body(build());
	}

}