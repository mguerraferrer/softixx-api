package softixx.api.exception;

import lombok.Getter;
import softixx.api.wrapper.WResponse;
import softixx.api.wrapper.WThrowable;

@Getter
public abstract class BaseException extends Exception {
	private static final long serialVersionUID = -189365452227508599L;
	
	protected final Object[] params;
	protected final String exception;
	protected final WResponse response;
	protected final WThrowable throwable;
	
	protected BaseException() {
		super();
		
		this.exception = null;
		this.params = null;
		this.response = null;
		this.throwable = null;
	}
	
	protected BaseException(String message) {
		super(message);
		
		this.exception = null;
		this.params = null;
		this.response = null;
		this.throwable = null;
	}
	
	protected BaseException(final Throwable cause) {
		super(cause);
		
		this.exception = cause.getMessage();
		this.params = null;
		this.response = null;
		this.throwable = null;
	}
	
	protected BaseException(final String message, final Throwable cause) {
		super(message, cause);
		
		this.exception = cause.getMessage();
		this.params = null;
		this.response = null;
		this.throwable = null;
	}
	
	protected BaseException(final String message, final Object[] params, final Throwable cause) {
		super(message, cause);
		
		this.exception = cause.getMessage();
		this.params = params;
		this.response = null;
		this.throwable = null;
	}
	
	protected BaseException(String message, String cause) {
		super(message);
		
		this.exception = cause;
		this.params = null;
		this.response = null;
		this.throwable = null;
	}
	
	protected BaseException(String message, Object[] params, String cause) {
		super(message);
		
		this.params = params;
		this.exception = cause;
		this.response = null;
		this.throwable = null;
	}
	
	protected BaseException(WResponse response) {
		super();
		
		this.response = response;
		this.params = null;
		this.exception = null;
		this.throwable = null;
	}
	
	protected BaseException(WThrowable throwable) {
		super();
		
		this.throwable = throwable;
		this.response = null;
		this.params = null;
		this.exception = null;
	}
	
}