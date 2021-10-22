package softixx.api.wrapper;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WThrowable {	
	private String redirectAfterError;
	private String exceptionMessage;
    private RedirectAttributes redirectAttributes;
    
    public WThrowable(final String redirectAfterError) {
    	this.redirectAfterError = redirectAfterError;
    }
    
    public WThrowable(final String redirectAfterError, final String exceptionMessage) {
    	this.redirectAfterError = redirectAfterError;
    	this.exceptionMessage = exceptionMessage;
    }
}
