package softixx.api.exception;

import softixx.api.wrapper.WResponse;

public class DaoException extends BaseException {
	private static final long serialVersionUID = -189365452227508599L;	
	
	public DaoException() {
		super();
	}
	
	public DaoException(String message) {
		super(message);
	}
	
	public DaoException(String message, String cause) {
		super(message, cause);
	}
	
	public DaoException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public DaoException(WResponse response) {
		super(response);
	}
	
}