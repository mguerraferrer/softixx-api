package softixx.api.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import softixx.api.enums.ENotificationType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Toastr {
	private String type;
	private String title;
	private String message;
	
	public Toastr(final String title, final String message, final ENotificationType toastrType) {
		this.title =  title;
		this.message = message;
		this.type = toastrType.value();
	}
	
	public static Toastr success(final String title, final String message) {
		return new Toastr(title, message, ENotificationType.SUCCESS);
	}
	
	public static Toastr info(final String title, final String message) {
		return new Toastr(title, message, ENotificationType.INFO);
	}
	
	public static Toastr warning(final String title, final String message) {
		return new Toastr(title, message, ENotificationType.WARNING);
	}
	
	public static Toastr error(final String title, final String message) {
		return new Toastr(title, message, ENotificationType.ERROR);
	}
}