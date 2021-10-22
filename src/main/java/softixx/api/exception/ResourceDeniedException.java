package softixx.api.exception;

import softixx.api.wrapper.WResponse;

public class ResourceDeniedException extends BaseException {
	private static final long serialVersionUID = -189365452227508599L;
	
	public ResourceDeniedException() {
		super();
	}
	
	public ResourceDeniedException(String message) {
		super(message);
	}
	
	public ResourceDeniedException(String message, String cause) {
		super(message, cause);
	}
	
	public ResourceDeniedException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public ResourceDeniedException(WResponse response) {
		super(response);
	}
	
}