package softixx.api.exception;

import softixx.api.wrapper.WResponse;

public class AnonymousUserException extends BaseException {
	private static final long serialVersionUID = -189365452227508599L;	
	
	public AnonymousUserException() {
		super();
	}
	
	public AnonymousUserException(String message) {
		super(message);
	}
	
	public AnonymousUserException(String message, String cause) {
		super(message, cause);
	}
	
	public AnonymousUserException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public AnonymousUserException(WResponse response) {
		super(response);
	}
	
}