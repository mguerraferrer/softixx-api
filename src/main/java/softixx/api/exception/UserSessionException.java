package softixx.api.exception;

import softixx.api.wrapper.WResponse;

public class UserSessionException extends BaseException {
	private static final long serialVersionUID = -189365452227508599L;	
	
	public UserSessionException() {
		super();
	}
	
	public UserSessionException(String message) {
		super(message);
	}
	
	public UserSessionException(String message, String cause) {
		super(message, cause);
	}
	
	public UserSessionException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public UserSessionException(WResponse response) {
		super(response);
	}
	
}