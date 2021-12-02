package softixx.api.custom;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.val;
import softixx.api.exception.BaseException;
import softixx.api.json.JResponse;
import softixx.api.util.UMessage;
import softixx.api.util.UPredicate;
import softixx.api.util.UValidator;
import softixx.api.wrapper.WResponse.ResponseJSON.ResponseMessage;

/**
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RestResponse implements Serializable {
	private static final long serialVersionUID = 8522735446650175360L;
	
	private HttpStatus httpStatus;
	private JResponse response;
	private Object data;
	@Singular
	private List<RestErrorMessage> errors;
	private final LocalDateTime timestamp = LocalDateTime.now();
	
	public RestResponse(JResponse response) {
		this.response = response;
	}
	
	public RestResponse(Object data) {
		this.data = data;
	}
	
	public RestResponse(List<RestErrorMessage> errors) {
		this.errors = errors;
	}
	
	public static ResponseEntity<RestResponse> unauthorized() {
		return unauthorized(UMessage.getMessage(UValidator.THROWABLE_UNAUTHORIZED_MESSAGE));
	}
	
	public static ResponseEntity<RestResponse> unauthorized(String message) {
		return reponseEntity(HttpStatus.UNAUTHORIZED, message);
	}
	
	public static ResponseEntity<RestResponse> forbidden() {
		return forbidden(UMessage.getMessage(UValidator.THROWABLE_RESOURCE_DENIED_MESSAGE));
	}
	
	public static ResponseEntity<RestResponse> forbidden(String message) {
		return reponseEntity(HttpStatus.FORBIDDEN, message);
	}
	
	public static ResponseEntity<RestResponse> notFound() {
		return notFound(UMessage.getMessage(UValidator.THROWABLE_RESOURCE_NOT_AVAILABLE_MESSAGE));
	}
	
	public static ResponseEntity<RestResponse> notFound(String message) {
		return reponseEntity(HttpStatus.NOT_FOUND, message);
	}
	
	public RestResponse(BaseException ex) {
		val errorsFromResponse = new ArrayList<RestErrorMessage>();
		if (UValidator.isNotEmpty(ex)) {
			if (UValidator.isNotEmpty(ex.getResponse())) {
				val responseJSON = ex.getResponse().getResponseJSON();
				if (UValidator.isNotEmpty(responseJSON)) {
					if (UValidator.isNotEmpty(responseJSON.getErrors())) {
						val errors = responseJSON.getErrors().stream()
															 .filter(filterResponse())
															 .filter(Objects::nonNull)
															 .map(this::mapToError)
															 .collect(Collectors.toList());
						
						if (UValidator.isNotEmpty(errors)) {
							errorsFromResponse.addAll(errors);
						}
					}
					
					if (UValidator.isNotEmpty(responseJSON.getMessage())) {
						val errorMessage = mapToError(responseJSON.getMessage());
						val errors = List.of(errorMessage);
						errorsFromResponse.addAll(errors);
					}
				}
				
				val responseError = ex.getResponse().getError();
				if (UValidator.isNotEmpty(responseError)) {
					errorsFromResponse(errorsFromResponse, responseError);
				}
				
				val responseMessage = ex.getResponse().getMessage();
				if (UValidator.isNotEmpty(responseMessage)) {
					errorsFromResponse(errorsFromResponse, responseMessage);
				}
			}
			
			if (UValidator.isNotEmpty(ex.getMessage()) && UValidator.isNotEmpty(ex.getParams())) {
				val message = UMessage.getMessage(ex.getMessage(), ex.getParams());
				errorsFromResponse(errorsFromResponse, message);
			} else if (UValidator.isNotEmpty(ex.getMessage())) {
				errorsFromResponse(errorsFromResponse, ex.getMessage());
			} else if (UValidator.isNotEmpty(ex.getLocalizedMessage())) {
				errorsFromResponse(errorsFromResponse, ex.getLocalizedMessage());
			}
		}
		
		if (UValidator.isEmpty(errorsFromResponse)) {
			val message = UMessage.getMessage(UValidator.UNEXPECTED_ERROR);
			errorsFromResponse(errorsFromResponse, message);
		}
		
		this.errors = errorsFromResponse.stream()
										.filter(UPredicate.distinctByKey(RestErrorMessage::getError))
										.collect(Collectors.toList());
	}
	
	public static RestResponseBuilder builder() {
		return new RestResponseBuilder();
	}
	
	private static ResponseEntity<RestResponse> reponseEntity(HttpStatus status, String message) {
		val errorDetails = RestResponse
							.builder()
							.status(status)
							.message(message)
							.build();
		
		return ResponseEntity.status(status).body(errorDetails);
	}
	
	private List<RestErrorMessage> errorsFromResponse(List<RestErrorMessage> errorsFromResponse, String message) {
		val errors = List.of(RestErrorMessage.builder().error(message).build());
		errorsFromResponse.addAll(errors);
		return errorsFromResponse;
	}
	
	private static Predicate<ResponseMessage> filterResponse() {
		return p -> UValidator.isNotEmpty(p.getDefaultMessage()) || UValidator.isNotEmpty(p.getError());
	}
	
	private RestErrorMessage mapToError(ResponseMessage rm) {
		String error = null;
		if (UValidator.isNotEmpty(rm.getDefaultMessage())) {
			error = rm.getDefaultMessage();
		} else if (UValidator.isNotEmpty(rm.getError())) {
			error = rm.getError();
		}
		
		return RestErrorMessage
				.builder()
				.error(error)
				.build();
	}
}