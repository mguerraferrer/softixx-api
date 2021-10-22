package softixx.api.exception;

import softixx.api.wrapper.WResponse;

public class ResponseException extends BaseException {
	private static final long serialVersionUID = -189365452227508599L;	
	
	public ResponseException() {
		super();
	}
	
	public ResponseException(String message) {
		super(message);
	}
	
	public ResponseException(String message, String cause) {
		super(message, cause);
	}
	
	public ResponseException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public ResponseException(WResponse response) {
		super(response);
	}
	
}