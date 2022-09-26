package softixx.api.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import softixx.api.custom.RestErrorMessage;
import softixx.api.custom.RestResponse;
import softixx.api.enums.EApiGeneric;
import softixx.api.enums.EIziToastPosition;
import softixx.api.enums.ENotificationType;
import softixx.api.exception.ResponseException;
import softixx.api.json.JGenericResponse;
import softixx.api.json.JResponse;
import softixx.api.json.JResponse.JConfirmation;
import softixx.api.json.JResponse.JNotification;
import softixx.api.json.JResponse.JNotification.CssStyleClass;
import softixx.api.json.JResponse.JNotification.MessageType;
import softixx.api.notification.IziToast;
import softixx.api.notification.Swal;
import softixx.api.notification.Toastr;
import softixx.api.wrapper.WResponse;
import softixx.api.wrapper.WResponse.ResponseJSON;
import softixx.api.wrapper.WResponse.ResponseJSON.ResponseError;
import softixx.api.wrapper.WResponse.ResponseJSON.ResponseMessage;

@Slf4j
public class UResponse {
	private static String REQUIRED_MESSAGE;
	private static String NOT_EMPTY_MESSAGE;
	private static String INVALID_ALPHABETIC_MESSAGE;
	private static String INVALID_NUMERIC_MESSAGE;
	private static String INVALID_EMAIL_MESSAGE;
	private static String INVALID_PHONE_MESSAGE;
	private static String INVALID_FORMAT_MESSAGE;
	private static String INVALID_FIELD_MESSAGE;
	private static String INVALID_FIELD_GREATER_THAN_VALIDATION;
	private static String INVALID_MIN_LENGTH_VALIDATION;
	private static String INVALID_MAX_LENGTH_VALIDATION;
	private static String INVALID_MIN_MAX_LENGTH_VALIDATION;
	private static String INVALID_REGEXP_VALIDATION;
	private static String INVALID_OLD_PASSWORD_MESSAGE;
	private static String INVALID_PASSWORD_DIFFERENT_MESSAGE;
	private static String INVALID_PASSWORD_MATCHING_MESSAGE;
	private static String INVALID_PASSWORD_MESSAGE;
	private static String USER_ALREADY_EXISTS_MESSAGE;
	private static String USER_RFC_ALREADY_EXISTS_MESSAGE;
	private static String USER_BNAME_ALREADY_EXISTS_MESSAGE;
	private static String USER_UNKNOWN_MESSAGE;
	private static String SINGLE_FILE_IMG_MISSING_MESSAGE;
	private static String MULTIPLE_FILE_IMG_MISSING_MESSAGE;
	private static String SINGLE_FILE_MISSING_MESSAGE;
	private static String MULTIPLE_FILE_MISSING_MESSAGE;
	private static String INVALID_FILE_EXTENSION_MESSAGE;
	private static String INVALID_FILE_SIZE_MESSAGE;
	private static String INVALID_FILE_SIZE_VALIDATION;
	private static String INVALID_DATE_OUT_RANGE_MESSAGE;
	private static String INVALID_ZIP_CODE_MESSAGE;
	private static String USER_NO_AUTHENTICATED_ACTION_MESSAGE;
	private static String INVALID_COUPON_MESSAGE;
	private static String INVALID_USED_COUPON_MESSAGE;
	
	private static List<String> errorMessages;
	private static Map<String, Integer> errorHierarchy;
	private static Map<String, Integer> customErrorHierarchy;
	private static boolean isCustomizedHierarchy = false;
	
	private UResponse() {
		throw new IllegalStateException("Utility class");
	}
	
	public static void instance(Map<String, Integer> errorHierarchy) {
		customErrorHierarchy = errorHierarchy;
		isCustomizedHierarchy = true;
	}
	
	public static RestResponse restResponse(BindingResult bindingResult) {
		try {
			
			val errors = bindingResult.getAllErrors().stream()
													 .filter(FieldError.class::isInstance)
													 .map(FieldError.class::cast)
													 .filter(Objects::nonNull)
													 .map(RestErrorMessage::new)
													 .filter(Objects::nonNull)
													 .collect(Collectors.toList());
			
			if (UValidator.isNotEmpty(errors)) {
				return RestResponse
						.builder()
						.errors(errors)
						.build();
			}
			
		} catch (Exception e) {
			log.error("ResponseUtil#restResponse error - {}", e);
		}
		return null;
	}
	
	public static RestResponse restResponse(JResponse response) {
		return new RestResponse(response);
	}
	
	public static RestResponse restResponse(Object data) {
		return new RestResponse(data);
	}
	
	public static RestResponse restResponse(List<ErrorMessage> errors) {
		return new RestResponse(errors);
	}
	
	public static ResponseEntity<RestResponse> badRequest(RestResponse rs) {
		return responseError(HttpStatus.BAD_REQUEST, rs);
	}
	
	public static ResponseEntity<RestResponse> internalServerError(RestResponse rs) {
		return responseError(HttpStatus.INTERNAL_SERVER_ERROR, rs);
	}
	
	public static ResponseEntity<RestResponse> responseError(HttpStatus status, RestResponse rs) {
		return ResponseEntity.status(status).body(rs);
	}
	
	public static WResponse response(String message) {
		return new WResponse(message);
	}

	public static WResponse response(String message, String error) {
		return new WResponse(message, error);
	}

	public static WResponse response(BindingResult bindingResult, String error) {
		init();
		val errorMap = new HashMap<String, ErrorMessage>();
		val bindingErrors = bindingResult.getAllErrors();

		for (val obj : bindingErrors) {
			if (obj instanceof FieldError) {
				val field = ((FieldError) obj).getField();
            	val msg = obj.getDefaultMessage();

            	val em = errorMap.entrySet().stream()
            								.filter(map -> map.getKey().equals(field))
            								.map(map -> map.getValue())
            								.findAny().orElse(null);
            	if (em == null) {
            		errorMap.put(field, getMessage(msg));
            	} else {
            		val evaluateError = evaluateError(field, em, msg);
            		if (evaluateError != null && evaluateError.getError() != null && !em.getError().equalsIgnoreCase(evaluateError.getError())) {
                    	errorMap.replace(field, evaluateError);
            		}
            	}
			}
		}

		val result = errorMap.entrySet().stream()
										.map(item -> mapped(item))
			 	  				  		.map(item -> "{\"field\":\"" + item.getField() + "\",\"defaultMessage\":\"" + item.getError() + "\"}")
			 	  				  		.collect(Collectors.joining(","));
		
		val message = "[" + result + "]";
		return new WResponse(message, error);
	}

	public static List<ErrorMessage> response(BindingResult bindingResult) {
		init();
		val errorMap = new HashMap<String, ErrorMessage>();
		val bindingErrors = bindingResult.getAllErrors();

		for (val obj : bindingErrors) {
			if (obj instanceof FieldError) {
				val field = ((FieldError) obj).getField();
            	val msg = obj.getDefaultMessage();

            	val em = errorMap.entrySet().stream()
            								.filter(map -> map.getKey().equals(field))
            								.map(map -> map.getValue())
            								.findAny().orElse(null);
            	if (em == null) {
            		errorMap.put(field, getMessage(msg));
            	} else {
            		val evaluateError = evaluateError(field, em, msg);
            		if (evaluateError != null && evaluateError.getError() != null && !em.getError().equalsIgnoreCase(evaluateError.getError())) {
                    	errorMap.replace(field, evaluateError);
            		}
            	}
			}
		}

		return errorMap.entrySet().stream()
								  .map(item -> mapped(item))
								  .collect(Collectors.toList());
	}
	
	public static WResponse response(final String errorCode, final String field, final String cause) {
		val errorMessage = UMessage.getMessage(errorCode);
		val message = ResponseMessage
						.builder()
						.field(field)
						.defaultMessage(errorMessage)
						.error(cause != null ? cause : null)
						.build();
				
		val responseJSON = new ResponseJSON();
		responseJSON.setMessage(message);
		
		return new WResponse(responseJSON);
	}

	public static WResponse response(final String errorCode, final Object[] params, final String field, final String cause) {
		val errorMessage = UMessage.getMessage(errorCode, params);
		val message = ResponseMessage
						.builder()
						.field(field)
						.defaultMessage(errorMessage)
						.error(cause != null ? cause : null)
						.build();
				
		val responseJSON = new ResponseJSON();
		responseJSON.setMessage(message);
		
		return new WResponse(responseJSON);
	}

	public static WResponse response(final String errorCode, final ResponseError responseError, final EApiGeneric cause) {
		val errorMessage = UMessage.getMessage(errorCode);
		
		String description = null;
		String action = null;
		String url = null;
		
		if (responseError != null) {
			description = responseError.getDescription();
			action = responseError.getAction();
			url = responseError.getUrl();
		}
		
		val message = ResponseMessage
						.builder()
						.defaultMessage(errorMessage)
						.error(cause != null ? cause.value() : null)
						.description(description)
						.action(action)
						.url(url)
						.build();
				
		val responseJSON = new ResponseJSON();
		responseJSON.setMessage(message);
		
		return new WResponse(responseJSON);
	}

	public static WResponse response(final String errorCode, final ResponseError responseError, final Object[] params, final String cause) {
		val errorMessage = UMessage.getMessage(errorCode, params);
		
		String description = null;
		String action = null;
		String url = null;
		
		if (responseError != null) {
			description = responseError.getDescription();
			action = responseError.getAction();
			url = responseError.getUrl();
		}
		
		val message = ResponseMessage
						.builder()
						.defaultMessage(errorMessage)
						.error(cause != null ? cause : null)
						.description(description)
						.action(action)
						.url(url)
						.build();
				
		val responseJSON = new ResponseJSON();
		responseJSON.setMessage(message);
		
		return new WResponse(responseJSON);
	}	
	
	public static ResponseEntity<JGenericResponse> confirmation(final String message) {
		val response = confirmationJson(message);
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
	}
	
	public static ResponseEntity<JGenericResponse> success() {
		val response = new JResponse();
		response.setSuccess(Boolean.TRUE);
		
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
	}
	
	public static ResponseEntity<Object> success(Object object) {
		return ResponseEntity.status(HttpStatus.OK).body(object);
	}
	
	public static ResponseEntity<JGenericResponse> response(final JResponse response) {
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
	}

	public static <T> ResponseEntity<T> response(T object) {
		return new ResponseEntity<>(object, new HttpHeaders(), HttpStatus.OK);
	}
	
	public static ResponseEntity<JGenericResponse> error() {
		val response = new JResponse();
		response.setError(Boolean.TRUE);
		
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
	}
	
	public static ResponseEntity<JGenericResponse> error(final JResponse response) {
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponse);
	}

	public static ResponseEntity<Object> error(final Object object) {
		return new ResponseEntity<>(object, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public static String responseStr(Object object) throws ResponseException {
		try {
			
			val mapper = new ObjectMapper();
			return mapper.writeValueAsString(object); 
			
		} catch (Exception e) {
			val wResponse = response(UMessage.getMessage(UValidator.UNEXPECTED_ERROR), null, EApiGeneric.MAPPED_EXCEPTION);
			throw new ResponseException(wResponse);
		}
	}
	
	public static ResponseEntity<WResponse> handleBindException(final BindingResult bindingResult, final WebRequest request, @Nullable final String error) {
		val bindingErrors = bindingResult.getAllErrors();
		val errors = bindingErrors.stream()
							 	  .filter(FieldError.class::isInstance)
							 	  .map(FieldError.class::cast)
							 	  .map(item -> "{\"field\":\"" + item.getField() + "\",\"defaultMessage\":\"" + item.getDefaultMessage() + "\"}")
							 	  .collect(Collectors.joining(","));
		val resultJSON = "[" + errors + "]";
		val response = new WResponse(resultJSON, error);
		
		request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, new Exception(), RequestAttributes.SCOPE_REQUEST);
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	public static String response(final List<ObjectError> allErrors) throws JsonProcessingException {
		val errors = allErrors.stream()
							  .filter(FieldError.class::isInstance)
							  .map(FieldError.class::cast)
							  .map(item -> ResponseMessage
									  		.builder()
									  		.field(item.getField())
									  		.defaultMessage(item.getDefaultMessage())
									  		.build())
							  .collect(Collectors.toList());
		
		val responseJson = ResponseJSON
							.builder()
							.clearErrors()
							.errors(errors)
							.build();
		
		val mapper = new ObjectMapper();
		return mapper.writeValueAsString(new WResponse(responseJson));
	}
	
	public static JResponse responseOK() {
		val response = new JResponse();
		response.setSuccess(true);
		response.setSessionAlive(true);
		return response;
	}
	
	public static JResponse responseJson(final boolean isSessionAlive, final boolean isError, final String messageDetail, final MessageType messageType, final CssStyleClass cssStyleClass) {
		return responseJson(isSessionAlive, isError, messageDetail, messageType, cssStyleClass, false, null);
	}
	
	public static JResponse responseJson(final boolean isSessionAlive, final boolean isError, final String messageDetail, final MessageType messageType, final CssStyleClass cssStyleClass, final boolean isDismiss) {
		return responseJson(isSessionAlive, isError, messageDetail, messageType, cssStyleClass, isDismiss, null);
	}
	
	public static JResponse responseJsonSuccess(final boolean isSessionAlive, final String messageDetail) {
		return responseJson(isSessionAlive, false, messageDetail, MessageType.PAGE, CssStyleClass.SUCCESS, false, null);
	}
	
	public static JResponse responseJsonError(final boolean isSessionAlive, final String messageDetail) {
		return responseJson(isSessionAlive, false, messageDetail, MessageType.PAGE, CssStyleClass.ERROR, false, null);
	}
	
	public static JResponse responseJsonInfo(final boolean isSessionAlive, final String messageDetail) {
		return responseJson(isSessionAlive, false, messageDetail, MessageType.PAGE, CssStyleClass.INFO, false, null);
	}
	
	public static JResponse responseJsonWarning(final boolean isSessionAlive, final String messageDetail) {
		return responseJson(isSessionAlive, false, messageDetail, MessageType.PAGE, CssStyleClass.WARNING, false, null);
	}
	
	public static JResponse responseJson(final boolean isSessionAlive, final boolean isError, final String messageDetail, final MessageType messageType, final CssStyleClass cssStyleClass, final boolean isDismiss, final Integer fadeOut) {
		val notification = new JNotification();
		notification.setMessage(UMessage.getMessage(messageDetail));
		notification.setCssStyleClass(cssStyleClass.value());
		notification.setPageMessage(messageType.equals(MessageType.PAGE));
		notification.setModalPanelMessage(messageType.equals(MessageType.MODAL));
		notification.setPanelMessage(messageType.equals(MessageType.PANEL));
		notification.setDismiss(isDismiss);
		notification.setFadeOut(fadeOut);
		
		val response = new JResponse();
		response.setNotification(notification);
		response.setSuccess(!isError);
		response.setError(isError);
		response.setSessionAlive(isSessionAlive);
		
		return response;
	}
	
	public static JResponse confirmationJson(final String message) {
		return confirmationJsonRisk(message, false);
	}
	
	public static JResponse confirmationJsonRisk(final String message, final Boolean showRisk) {
		val confirmation = new JConfirmation(message, showRisk);
		
		val response = new JResponse();
		response.setConfirmation(confirmation);
		response.setSuccess(Boolean.TRUE);
		
		return response;
	}
	
	public static JResponse swal(final String messageDetail) {
		return swal(true, messageDetail, false);
	}
	
	public static JResponse swal(final boolean isSessionAlive, final String messageDetail) {
		return swal(isSessionAlive, messageDetail, false);
	}
	
	public static JResponse swal(final String messageDetail, final boolean isError) {
		return swal(true, messageDetail, isError);
	}
	
	public static JResponse swal(final boolean isSessionAlive, final String messageDetail, final boolean isError) {
		val swalType = isError ? ENotificationType.ERROR : ENotificationType.SUCCESS;
		val swal = Swal.swal(messageDetail, swalType);
		
		val notification = JNotification
							.builder()
							.swal(swal)
							.swalNotification(true)
							.build();

		return JResponse
				.builder()
				.notification(notification)
				.success(!isError)
				.error(isError)
				.sessionAlive(isSessionAlive)
				.build();
	}
	
	public static JResponse iziToast(final String message, final EIziToastPosition iziToastPosition, final boolean isError) {
		val iziToastType = !isError ? ENotificationType.SUCCESS : ENotificationType.ERROR; 
		return iziToast(true, iziToastType, null, message, iziToastPosition);
	}
	
	public static JResponse iziToast(final boolean isSessionAlive, final String message, final EIziToastPosition iziToastPosition, final boolean isError) {
		val iziToastType = !isError ? ENotificationType.SUCCESS : ENotificationType.ERROR; 
		return iziToast(isSessionAlive, iziToastType, null, message, iziToastPosition);
	}
	
	public static JResponse iziToast(final ENotificationType iziToastType, final String message, final EIziToastPosition iziToastPosition) {
		return iziToast(true, iziToastType, null, message, iziToastPosition);
	}
	
	public static JResponse iziToast(final boolean isSessionAlive, final ENotificationType iziToastType, final String message, final EIziToastPosition iziToastPosition) {
		return iziToast(isSessionAlive, iziToastType, null, message, iziToastPosition);
	}
	
	public static JResponse iziToast(final boolean isSessionAlive, final ENotificationType iziToastType, final String title, final String message, final EIziToastPosition iziToastPosition) {
		val iziToast = IziToast.instance(iziToastType, title, message, iziToastPosition);
		
		val notification = new JNotification();
		notification.setIziToast(iziToast);
		notification.setIziToastNotification(Boolean.TRUE);
		
		val isError = iziToastType.value().equals("error");
		
		return JResponse.instance(notification, !isError, isError, isSessionAlive);
	}
	
	public static JResponse toastr(final String toastrTitle, final String toastrMsg, ENotificationType toastrType) {
		return toastr(true, toastrTitle, toastrMsg, toastrType);
	}
	
	public static JResponse toastr(final boolean isSessionAlive, final String toastrTitle, final String toastrMsg, ENotificationType toastrType) {
		val toastr = new Toastr();
		toastr.setTitle(toastrTitle);
		toastr.setMessage(toastrMsg);
		toastr.setType(toastrType.value());
		
		val notification = new JNotification();
		notification.setToastr(toastr);
		notification.setToastrNotification(Boolean.TRUE);
		
		val isError = toastrType.value().equals("error");
		
		return JResponse.instance(notification, !isError, isError, isSessionAlive);
	}
	
	private static void init() {
		REQUIRED_MESSAGE = i18nMessage(UValidator.REQUIRED_MESSAGE);
		NOT_EMPTY_MESSAGE = i18nMessage(UValidator.NOT_EMPTY_MESSAGE);
		INVALID_ALPHABETIC_MESSAGE = i18nMessage(UValidator.INVALID_ALPHABETIC_MESSAGE);
		INVALID_NUMERIC_MESSAGE = i18nMessage(UValidator.INVALID_NUMERIC_MESSAGE);
		INVALID_EMAIL_MESSAGE = i18nMessage(UValidator.INVALID_EMAIL_MESSAGE);
		INVALID_PHONE_MESSAGE = i18nMessage(UValidator.INVALID_PHONE_MESSAGE);
		INVALID_FORMAT_MESSAGE = i18nMessage(UValidator.INVALID_FORMAT_MESSAGE);
		INVALID_FIELD_MESSAGE = i18nMessage(UValidator.INVALID_FIELD_MESSAGE);
		INVALID_FIELD_GREATER_THAN_VALIDATION = i18nMessage(UValidator.INVALID_FIELD_GREATER_THAN_VALIDATION);
		INVALID_MIN_LENGTH_VALIDATION = i18nMessage(UValidator.INVALID_MIN_LENGTH_VALIDATION);
		INVALID_MAX_LENGTH_VALIDATION = i18nMessage(UValidator.INVALID_MAX_LENGTH_VALIDATION);
		INVALID_MIN_MAX_LENGTH_VALIDATION = i18nMessage(UValidator.INVALID_MIN_MAX_LENGTH_VALIDATION);
		INVALID_REGEXP_VALIDATION = i18nMessage(UValidator.INVALID_REGEXP_VALIDATION);
		INVALID_OLD_PASSWORD_MESSAGE = i18nMessage(UValidator.INVALID_OLD_PASSWORD_MESSAGE);
		INVALID_PASSWORD_DIFFERENT_MESSAGE = i18nMessage(UValidator.INVALID_PASSWORD_DIFFERENT_MESSAGE);
		INVALID_PASSWORD_MATCHING_MESSAGE = i18nMessage(UValidator.INVALID_PASSWORD_MATCHING_MESSAGE);
		INVALID_PASSWORD_MESSAGE = i18nMessage(UValidator.INVALID_PASSWORD_MESSAGE);
		USER_ALREADY_EXISTS_MESSAGE = i18nMessage(UValidator.USER_ALREADY_EXISTS_MESSAGE);
		USER_RFC_ALREADY_EXISTS_MESSAGE = i18nMessage(UValidator.USER_RFC_ALREADY_EXISTS_MESSAGE);
		USER_BNAME_ALREADY_EXISTS_MESSAGE = i18nMessage(UValidator.USER_BNAME_ALREADY_EXISTS_MESSAGE);
		USER_UNKNOWN_MESSAGE = i18nMessage(UValidator.USER_UNKNOWN_MESSAGE);
		SINGLE_FILE_IMG_MISSING_MESSAGE = i18nMessage(UValidator.SINGLE_FILE_IMG_MISSING_MESSAGE);
		MULTIPLE_FILE_IMG_MISSING_MESSAGE = i18nMessage(UValidator.MULTIPLE_FILE_IMG_MISSING_MESSAGE);
		SINGLE_FILE_MISSING_MESSAGE = i18nMessage(UValidator.SINGLE_FILE_MISSING_MESSAGE);
		MULTIPLE_FILE_MISSING_MESSAGE = i18nMessage(UValidator.MULTIPLE_FILE_MISSING_MESSAGE);
		INVALID_FILE_EXTENSION_MESSAGE = i18nMessage(UValidator.INVALID_FILE_EXTENSION_MESSAGE);
		INVALID_FILE_SIZE_MESSAGE = i18nMessage(UValidator.INVALID_FILE_SIZE_MESSAGE);
		INVALID_FILE_SIZE_VALIDATION = i18nMessage(UValidator.INVALID_FILE_SIZE_VALIDATION);
		INVALID_DATE_OUT_RANGE_MESSAGE = i18nMessage(UValidator.INVALID_DATE_OUT_RANGE_MESSAGE);
		INVALID_ZIP_CODE_MESSAGE = i18nMessage(UValidator.INVALID_ZIP_CODE_MESSAGE);
		USER_NO_AUTHENTICATED_ACTION_MESSAGE = i18nMessage(UValidator.USER_NO_AUTHENTICATED_ACTION_MESSAGE);
		INVALID_COUPON_MESSAGE = i18nMessage(UValidator.INVALID_COUPON_MESSAGE);
		INVALID_USED_COUPON_MESSAGE = i18nMessage(UValidator.INVALID_USED_COUPON_MESSAGE);
		
		errorMessages = Stream.of(
			REQUIRED_MESSAGE, NOT_EMPTY_MESSAGE, INVALID_ALPHABETIC_MESSAGE, INVALID_NUMERIC_MESSAGE, INVALID_EMAIL_MESSAGE, INVALID_PHONE_MESSAGE, INVALID_FORMAT_MESSAGE, 
			INVALID_FIELD_MESSAGE, INVALID_FIELD_GREATER_THAN_VALIDATION, INVALID_MIN_LENGTH_VALIDATION, INVALID_MAX_LENGTH_VALIDATION, INVALID_MIN_MAX_LENGTH_VALIDATION, INVALID_REGEXP_VALIDATION, 
			INVALID_OLD_PASSWORD_MESSAGE, INVALID_PASSWORD_DIFFERENT_MESSAGE, INVALID_PASSWORD_MATCHING_MESSAGE, INVALID_PASSWORD_MESSAGE, USER_ALREADY_EXISTS_MESSAGE,
			USER_RFC_ALREADY_EXISTS_MESSAGE, USER_BNAME_ALREADY_EXISTS_MESSAGE, USER_UNKNOWN_MESSAGE, SINGLE_FILE_IMG_MISSING_MESSAGE, MULTIPLE_FILE_IMG_MISSING_MESSAGE, SINGLE_FILE_MISSING_MESSAGE,
			MULTIPLE_FILE_MISSING_MESSAGE, INVALID_FILE_EXTENSION_MESSAGE, INVALID_FILE_EXTENSION_MESSAGE, INVALID_FILE_SIZE_MESSAGE, INVALID_FILE_SIZE_VALIDATION, INVALID_DATE_OUT_RANGE_MESSAGE,
			INVALID_ZIP_CODE_MESSAGE, USER_NO_AUTHENTICATED_ACTION_MESSAGE, INVALID_COUPON_MESSAGE, INVALID_USED_COUPON_MESSAGE
		).collect(Collectors.toList());
		
		//##### Definiendo la jerarquía de los errores
    	errorHierarchy = new HashMap<>();
    	errorHierarchy.put(USER_NO_AUTHENTICATED_ACTION_MESSAGE, 1);
    	errorHierarchy.put(REQUIRED_MESSAGE, 1);
    	errorHierarchy.put(NOT_EMPTY_MESSAGE, 1);
    	errorHierarchy.put(INVALID_ALPHABETIC_MESSAGE, 2);
    	errorHierarchy.put(INVALID_NUMERIC_MESSAGE, 2);
    	errorHierarchy.put(INVALID_EMAIL_MESSAGE, 3);
    	errorHierarchy.put(INVALID_PHONE_MESSAGE, 3);
    	errorHierarchy.put(INVALID_FORMAT_MESSAGE, 3);
    	errorHierarchy.put(INVALID_FIELD_MESSAGE, 3);
    	errorHierarchy.put(INVALID_REGEXP_VALIDATION, 4);
    	errorHierarchy.put(USER_ALREADY_EXISTS_MESSAGE, 4);
    	errorHierarchy.put(USER_RFC_ALREADY_EXISTS_MESSAGE, 4);
    	errorHierarchy.put(USER_BNAME_ALREADY_EXISTS_MESSAGE, 4);
    	errorHierarchy.put(USER_UNKNOWN_MESSAGE, 4);
    	errorHierarchy.put(INVALID_FIELD_GREATER_THAN_VALIDATION, 4);
    	errorHierarchy.put(INVALID_MIN_LENGTH_VALIDATION, 4);
    	errorHierarchy.put(INVALID_MAX_LENGTH_VALIDATION, 4);
    	errorHierarchy.put(INVALID_MIN_MAX_LENGTH_VALIDATION, 4);	    	
    	errorHierarchy.put(INVALID_OLD_PASSWORD_MESSAGE, 4);
    	errorHierarchy.put(INVALID_PASSWORD_DIFFERENT_MESSAGE, 5);
    	errorHierarchy.put(INVALID_PASSWORD_MATCHING_MESSAGE, 6);
    	errorHierarchy.put(INVALID_PASSWORD_MESSAGE, 4);
    	errorHierarchy.put(SINGLE_FILE_IMG_MISSING_MESSAGE, 3);
    	errorHierarchy.put(MULTIPLE_FILE_IMG_MISSING_MESSAGE, 3);
    	errorHierarchy.put(SINGLE_FILE_MISSING_MESSAGE, 3);
    	errorHierarchy.put(MULTIPLE_FILE_MISSING_MESSAGE, 3);
    	errorHierarchy.put(INVALID_FILE_EXTENSION_MESSAGE, 4);
    	errorHierarchy.put(INVALID_FILE_SIZE_MESSAGE, 4);
    	errorHierarchy.put(INVALID_FILE_SIZE_VALIDATION, 4);
    	errorHierarchy.put(INVALID_DATE_OUT_RANGE_MESSAGE, 2);
    	errorHierarchy.put(INVALID_ZIP_CODE_MESSAGE, 3);
    	errorHierarchy.put(INVALID_COUPON_MESSAGE, 3);
    	errorHierarchy.put(INVALID_USED_COUPON_MESSAGE, 4);
		
    	if (isCustomizedHierarchy && customErrorHierarchy != null && !customErrorHierarchy.isEmpty()) {
			for (val entry : customErrorHierarchy.entrySet()) {
				val key = i18nMessage(entry.getKey());
				val value = entry.getValue();
				errorMessages.add(key);
				errorHierarchy.put(key, value);
			}
		}
    }
	
	private static ErrorMessage getMessage(final String msg) {
		String key = null;
		String alternative = null;
		
		for (val errorMessage : errorMessages) {
			if (msg.equalsIgnoreCase(errorMessage)) {
				key = errorMessage;
			} else if (msg.contains(INVALID_FIELD_GREATER_THAN_VALIDATION)) {
	    		key = INVALID_FIELD_GREATER_THAN_VALIDATION;
	    		alternative = msg;
	    	} else if (msg.contains(INVALID_MIN_LENGTH_VALIDATION)) {
	    		key = INVALID_MIN_LENGTH_VALIDATION;
	    		alternative = msg;
	    	} else if (msg.contains(INVALID_MAX_LENGTH_VALIDATION)) {
	    		key = INVALID_MAX_LENGTH_VALIDATION;
	    		alternative = msg;
	    	} else if (msg.contains(INVALID_MIN_MAX_LENGTH_VALIDATION)) {
	    		key = INVALID_MIN_MAX_LENGTH_VALIDATION;
	    		alternative = msg;
	    	} else if (msg.contains(INVALID_REGEXP_VALIDATION)) {
	    		key = INVALID_REGEXP_VALIDATION;
	    		alternative = INVALID_FIELD_MESSAGE;
	    	} else if (msg.contains(INVALID_FILE_SIZE_VALIDATION)) {
	    		key = INVALID_FILE_SIZE_VALIDATION;
	    		alternative = msg;
	    	}
			
			if (key != null) {
				break;
			}
		}
		
		if (key == null) {
			key = i18nMessage(msg);
		}
		
    	val errorMessage = new ErrorMessage();
    	errorMessage.setError(key);
    	errorMessage.setAlternativeError(alternative);
    	return errorMessage;
	}
	
	private static ErrorMessage evaluateError(final String field, final ErrorMessage oldError, final String newMsg) {
    	val newError = getMessage(newMsg);
    	if (newError != null) {
    		Integer oldHierarchy = null;
    		if (errorHierarchy.containsKey(oldError.getError())) {
    			oldHierarchy = errorHierarchy.get(oldError.getError()); 
    		}
    		
    		Integer newHierarchy = null;
    		if (errorHierarchy.containsKey(newError.getError())) {
    			newHierarchy = errorHierarchy.get(newError.getError()); 
    		}
    		
    		if (oldHierarchy != null && newHierarchy != null && newHierarchy < oldHierarchy) {
    			return newError;
    		}
    	}
    	return oldError;
    }
	
	@Data
    public static class ErrorMessage {
    	private String field;
    	private String error;
    	private String alternativeError;
    }
    
    private static ErrorMessage mapped(final Entry<String, ErrorMessage> entry) {
    	val field = entry.getKey();
		
		var error = entry.getValue().getError();
		if (entry.getValue().getAlternativeError() != null) {
			error = entry.getValue().getAlternativeError();
		}
		
		val errorMessage = new ErrorMessage();
		errorMessage.setField(field);
		errorMessage.setError(error);
		
		return errorMessage;
    }
    
    private static String i18nMessage(String key) {
    	return UMessage.getMessage(key);
	}

}