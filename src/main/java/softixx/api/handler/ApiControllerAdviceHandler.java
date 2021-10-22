package softixx.api.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.val;
import softixx.api.exception.BaseException;
import softixx.api.exception.ThrowableException;
import softixx.api.util.UMessage;
import softixx.api.util.UResponse;
import softixx.api.util.UValidator;
import softixx.api.wrapper.WResponse.ResponseJSON;

@ControllerAdvice
public class ApiControllerAdviceHandler extends ResponseEntityExceptionHandler {
	@Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        val result = ex.getBindingResult();
        val bodyOfResponse = UResponse.response(ex, "Invalid " + result.getObjectName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
	
	@ExceptionHandler({ BaseException.class })
	public ResponseEntity<?> handleBaseException(BaseException ex) {
		var response = ex.getResponse() != null 
				? ex.getResponse().getResponseJSON()
				: UMessage.getMessage(ex.getMessage(), ex.getParams());
		if(response == null) {
			response = new ResponseJSON(UMessage.getMessage(UValidator.UNEXPECTED_ERROR));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
	@ExceptionHandler({ ThrowableException.class })
	public String handleThrowableException(Model model, ThrowableException ex) {
		var redirect = ex.getThrowable().getRedirectAfterError();
		if(!redirect.startsWith("redirect:")) {
			if(redirect.startsWith("/")) {
				redirect = "redirect:" + redirect;
			} else {
				redirect = "redirect:/" + redirect;
			}
		}
		return redirect; 
	}	
}