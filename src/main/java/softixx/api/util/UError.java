package softixx.api.util;

import softixx.api.enums.EApiGeneric;
import softixx.api.wrapper.WResponse;

public class UError {

	public static final WResponse UNEXPECTED_ERROR = UResponse.response(UValidator.UNEXPECTED_ERROR, null, EApiGeneric.NULL);
	public static final WResponse DATA_EXCEPTION = UResponse.response(UValidator.UNEXPECTED_ERROR, null, EApiGeneric.DATA_INTEGRITY_VIOLATION_EXCEPTION);
	
}