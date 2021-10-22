package softixx.api.wrapper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WResponse {
    private String message;
    private String error;
    private ResponseJSON responseJSON;

    public WResponse(final String message) {
        super();
        this.message = message;
    }

    public WResponse(final String message, final String error) {
        super();
        this.message = message;
        this.error = error;
    }
    
    public WResponse(final ResponseJSON responseJSON) {
        super();
        this.responseJSON = responseJSON;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseJSON {
    	private ResponseMessage message;
    	@Singular
    	private List<ResponseMessage> errors;
    	
    	public ResponseJSON(final String defaultMessage) {
    		this.message = new ResponseMessage(defaultMessage);
    	}
    	
    	@Data
    	@Builder
        @NoArgsConstructor
        @AllArgsConstructor
    	public static class ResponseMessage {
    		private String field;
    		private String defaultMessage;
    		private String error;
    		private String description;
    		private String action;
    		private String url;
    		
    		public ResponseMessage(final String defaultMessage) {
    			this.defaultMessage = defaultMessage;
    		}
    	}
    	
    	@Data
    	@Builder
        @NoArgsConstructor
        @AllArgsConstructor
    	public static class ResponseError {
    		private String description;
    		private String action;
    		private String url;
    	}
    }

}