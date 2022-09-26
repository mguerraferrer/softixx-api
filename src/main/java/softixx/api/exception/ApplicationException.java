package softixx.api.exception;

import softixx.api.wrapper.WResponse;

@SuppressWarnings("serial")
public class ApplicationException extends BaseException {
	
	public ApplicationException() {
		super();
	}
	
	public ApplicationException(String message) {
		super(message);
	}
	
	public ApplicationException(String message, String cause) {
		super(message, cause);
	}
	
	public ApplicationException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public ApplicationException(WResponse response) {
		super(response);
	}
	
}