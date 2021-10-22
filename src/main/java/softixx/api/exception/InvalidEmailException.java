package softixx.api.exception;

import softixx.api.wrapper.WResponse;

public class InvalidEmailException extends BaseException {
	private static final long serialVersionUID = -189365452227508599L;	
	
	public InvalidEmailException() {
		super();
	}
	
	public InvalidEmailException(String message) {
		super(message);
	}
	
	public InvalidEmailException(String message, String cause) {
		super(message, cause);
	}
	
	public InvalidEmailException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public InvalidEmailException(WResponse response) {
		super(response);
	}
	
}