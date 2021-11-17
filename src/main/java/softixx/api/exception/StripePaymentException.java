package softixx.api.exception;

import softixx.api.wrapper.WResponse;

public class StripePaymentException extends BaseException {
	private static final long serialVersionUID = -189365452227508599L;	
	
	public StripePaymentException() {
		super();
	}
	
	public StripePaymentException(String message) {
		super(message);
	}
	
	public StripePaymentException(String message, String cause) {
		super(message, cause);
	}
	
	public StripePaymentException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public StripePaymentException(WResponse response) {
		super(response);
	}
	
}