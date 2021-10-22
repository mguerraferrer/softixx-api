package softixx.api.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import softixx.api.notification.IziToast;
import softixx.api.notification.Swal;
import softixx.api.notification.Toastr;
import softixx.api.notification.W2Tag;
import softixx.api.util.UMessage;
import softixx.api.wrapper.WResponse;
import softixx.api.wrapper.WResponse.ResponseJSON.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.val;
import lombok.AccessLevel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JResponse {
	@Default
	private boolean success = false;
	@Default
	private Boolean error = false;
	@Default
	private boolean licenseExpired = false;
	@Default
	private boolean sessionAlive = true;
	
	private String tabActivated;
	private String tabError;
	private String display;
	private String redirect;
	
	private JNotification notification;
	private JConfirmation confirmation;
	
	@Setter(AccessLevel.NONE)
	@Singular
	@Getter(AccessLevel.NONE)
	private List<JError> errors;
	@Singular("errorMap")
	private Map<String, String> errorMap;
	
	public static JResponse instance(JNotification notification, boolean isSuccess, boolean isError, boolean isSessionAlive) {
		val response = new JResponse();
		response.setNotification(notification);
		response.setSuccess(isSuccess);
		response.setError(isError);
		response.setSessionAlive(isSessionAlive);
		return response;
	}
	
	public static JResponse successInstance() {
		val response = new JResponse();
		response.setSuccess(true);
		return response;
	}
	
	public static JResponse errorInstance() {
		val response = new JResponse();
		response.setError(true);
		return response;
	}
	
	public static JResponse errorInstance(final List<JError> errors) {
		val response = new JResponse();
		response.setError(true);
		response.setErrors(errors);
		return response;
	}
	
	public List<JError> getErrors() {
		if(this.errors == null) {
			this.errors = new ArrayList<>();
		}
		return this.errors;
	}
	
	public void setErrors(final List<JError> errors) {
		this.errors = errors;
	}
	
	public void setErrors(final Map<String, String> errorMap) {
		this.errors = errorMap.entrySet()
				  			  .stream()
				  			  .map(item -> JError
				  					  		.builder()
				  					  		.field(item.getKey())
				  					  		.defaultMessage(item.getValue())
				  					  		.build())
				  			  .collect(Collectors.toList());
	}
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JNotification {
		@Default
		private boolean pageMessage = Boolean.FALSE;
		@Default
		private boolean modalPanelMessage = Boolean.FALSE;
		@Default
		private boolean panelMessage = Boolean.FALSE;
		@Default
		private boolean search = Boolean.FALSE;
		@Default
		private String cssStyleClass = CssStyleClass.SUCCESS.value;

		private String field;
		private String message;
		private String searchResultMessage;
		private String error;
		private String description;
		private String action;
		private String url;
		
		@Default
		private boolean dismiss = Boolean.FALSE;
		private Integer fadeOut;
		
		@Default
		private boolean toastrNotification = Boolean.FALSE;
		private Toastr toastr;
		
		@Default
		private boolean w2tagNotification = Boolean.FALSE;	
		private W2Tag w2Tag;
		
		@Default
		private boolean swalNotification = Boolean.FALSE;
		private Swal swal;
		
		@Default
		private boolean iziToastNotification = Boolean.FALSE;
		private IziToast iziToast;
		
		public JNotification(final String message, final MessageType messageType, final CssStyleClass cssStyleClass, final Boolean isDismiss) {
			this.message = message;
			this.pageMessage = messageType.equals(MessageType.PAGE);
			this.modalPanelMessage = messageType.equals(MessageType.MODAL);
			this.panelMessage = messageType.equals(MessageType.PANEL);
			this.cssStyleClass = cssStyleClass.value();
			this.dismiss = isDismiss;
		}
		
		public JNotification(final ResponseMessage error) {
			this.field = error.getField();
			this.message = error.getDefaultMessage();
			this.error = error.getError();
			this.description = error.getDescription();
			this.action = error.getAction();
			this.url = error.getUrl();
			this.pageMessage = Boolean.TRUE;
			this.dismiss = false;
			this.cssStyleClass = CssStyleClass.ERROR.value;
		}
		
		public static JNotification success(final String message) {
			return new JNotification(message, MessageType.PAGE, CssStyleClass.SUCCESS, false);
		}
		
		public static JNotification info(final String message) {
			return new JNotification(message, MessageType.PAGE, CssStyleClass.INFO, false);
		}
		
		public static JNotification warning(final String message) {
			return new JNotification(message, MessageType.PAGE, CssStyleClass.WARNING, false);
		}
		
		public static JNotification error(final String message) {
			return new JNotification(message, MessageType.PAGE, CssStyleClass.ERROR, false);
		}
		
		public static JNotification search(final boolean isSearch, final boolean isResultEmpty) {
			val notification = new JNotification();
			
			var searchResultEmpty = UMessage.getMessage("paginate.text.empty");
			if(isSearch) {
				notification.setSearch(Boolean.TRUE);
				searchResultEmpty = UMessage.getMessage("paginate.text.empty.search");
			}
			
			if(isResultEmpty) {
				notification.setSearchResultMessage(searchResultEmpty);
				notification.setPanelMessage(true);
				notification.setCssStyleClass(CssStyleClass.INFO.value());
			}
			
			return notification;
		}
		
		public static JNotification pageMessageError(final WResponse response) {
			val error = response.getResponseJSON().getMessage();
			return new JNotification(error);
		}
		
		public static String infoStyle() {
			return CssStyleClass.INFO.value;
		}
		
		public static String successStyle() {
			return CssStyleClass.SUCCESS.value;
		}
		
		public static String warningStyle() {
			return CssStyleClass.WARNING.value;
		}
		
		public static String errorStyle() {
			return CssStyleClass.ERROR.value;
		}
		
		public String cssStyleClass(Boolean isError) {
			return Boolean.FALSE.equals(isError) ? CssStyleClass.SUCCESS.value : CssStyleClass.ERROR.value;
		}
		
		public enum CssStyleClass {
			SUCCESS("alert alert-success"),
			ERROR("alert alert-danger"),
			INFO("alert alert-info"),
			WARNING("alert alert-warning");
			
			private final String value;

			CssStyleClass(String v) {
		        value = v;
		    }

		    public String value() {
		        return value;
		    }

		    public CssStyleClass fromValue(String v) {
		        return valueOf(v);
		    }
		}
		
		public enum MessageType {
			PAGE, MODAL, PANEL;
		}
	}
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JConfirmation {
		private String confirmationText;
		@Default
		private boolean showConfirmationRisk = false;
		
		public JConfirmation(final String confirmationText) {
			this.confirmationText = confirmationText;
			this.showConfirmationRisk = false;
		}
		
		public JConfirmation(final String confirmationText, final Boolean showConfirmationRisk) {
			this.confirmationText = confirmationText;
			this.showConfirmationRisk = showConfirmationRisk;
		}
	}
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JError {
		private String field;
		private String defaultMessage;
		private String error;		
	}
}