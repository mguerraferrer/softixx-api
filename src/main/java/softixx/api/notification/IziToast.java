package softixx.api.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import softixx.api.bean.IconBean;
import softixx.api.bean.IconBean.CustomIcon;
import softixx.api.enums.EFontAwesomeNotificationIcon;
import softixx.api.enums.EIziToastPosition;
import softixx.api.enums.ENotificationType;

/**
 * This class provides IziToast notifications. It has 2 different static instances: <br>
 * <b>IziToast.instance(final ENotificationType iziToastType, final String message, final EIziToastPosition iziToastPosition)</b> - This instance generate
 * a IziToast notification without title.
 * <b>IziToast.instance(final ENotificationType iziToastType, final String title, final String message, final EIziToastPosition iziToastPosition)</b> - This instance generate
 * a IziToast notification with title
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IziToast {
	private String type;
	private String title;
	private String message;
	private String cssClass;
	private String position;
	private String icon;
	
	public IziToast(final String type, final String title, final String message, final String cssClass, final String position) {
		this.type = type;
		this.title = title;
		this.message = message;
		this.cssClass = cssClass;
		this.position = position;
	}
	
	/**
	 * IziToast static instance (without) title and FontAwesome icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast instance(final ENotificationType iziToastType, final String message, final EIziToastPosition iziToastPosition) {
		return switch (iziToastType.value()) {
			case "success" -> success(message, iziToastPosition);
			case "warning" -> warning(message, iziToastPosition);
			case "info" -> info(message, iziToastPosition);
			case "error" -> error(message, iziToastPosition);
			case "dark" -> dark(message, iziToastPosition);
			default -> null;
		};
	}

	/**
	 * IziToast static instance with title and FontAwesome icon
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast instance(final ENotificationType iziToastType, final String title, final String message, final EIziToastPosition iziToastPosition) {
		return switch (iziToastType.value()) {
			case "success" -> success(title, message, iziToastPosition);
			case "warning" -> warning(title, message, iziToastPosition);
			case "info" -> info(title, message, iziToastPosition);
			case "error" -> error(title, message, iziToastPosition);
			case "dark" -> dark(title, message, iziToastPosition);
			default -> null;
		};
	}

	/**
	 * IziToast success message (without title and without icon)
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast successSimple(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.SUCCESS.value(), null, message, IziToastCssClass.SUCCESS.value, iziToastPosition.value());
	}
	
	/**
	 * IziToast success message (without title) using FontAwesome icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast success(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.SUCCESS.value(), null, message, IziToastCssClass.SUCCESS.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.SUCCESS.value());
	}

	/**
	 * IziToast success message with title using FontAwesome icon
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast success(final String title, final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.SUCCESS.value(), title, message, IziToastCssClass.SUCCESS.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.SUCCESS.value());
	}

	/**
	 * IziToast success message (without title) using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast success(final String message, final EIziToastPosition iziToastPosition, final String successIcon) {
		IconBean.successInstance(successIcon);
		return new IziToast(ENotificationType.SUCCESS.value(), null, message, IziToastCssClass.SUCCESS.value, iziToastPosition.value(), CustomIcon.SUCCESS.value());
	}

	/**
	 * IziToast success message with title using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast success(final String title, final String message, final EIziToastPosition iziToastPosition, final String successIcon) {
		IconBean.successInstance(successIcon);
		return new IziToast(ENotificationType.SUCCESS.value(), title, message, IziToastCssClass.SUCCESS.value, iziToastPosition.value(), CustomIcon.SUCCESS.value());
	}
	
	/**
	 * IziToast info message (without title and without icon)
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast infoSimple(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.INFO.value(), null, message, IziToastCssClass.INFO.value, iziToastPosition.value());
	}
	
	/**
	 * IziToast info message (without title) using FontAwesome icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast info(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.INFO.value(), null, message, IziToastCssClass.INFO.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.INFO.value());
	}

	/**
	 * IziToast info message with title using FontAwesome icon
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast info(final String title, final String message, EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.INFO.value(), title, message, IziToastCssClass.INFO.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.INFO.value());
	}
	
	/**
	 * IziToast info message (without title) using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast info(final String message, final EIziToastPosition iziToastPosition, final String infoIcon) {
		IconBean.infoInstance(infoIcon);
		return new IziToast(ENotificationType.INFO.value(), null, message, IziToastCssClass.INFO.value, iziToastPosition.value(), CustomIcon.INFO.value());
	}

	/**
	 * IziToast info message with title using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast info(final String title, final String message, EIziToastPosition iziToastPosition, final String infoIcon) {
		IconBean.infoInstance(infoIcon);
		return new IziToast(ENotificationType.INFO.value(), title, message, IziToastCssClass.INFO.value, iziToastPosition.value(), CustomIcon.INFO.value());
	}

	/**
	 * IziToast warning message (without title and without icon)
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast warningSimple(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.WARNING.value(), null, message, IziToastCssClass.WARNING.value, iziToastPosition.value());
	}
	
	/**
	 * IziToast warning message (without title) using FontAwesome icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast warning(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.WARNING.value(), null, message, IziToastCssClass.WARNING.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.WARNING.value());
	}

	/**
	 * IziToast warning message with title using FontAwesome icon
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast warning(final String title, final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.WARNING.value(), title, message, IziToastCssClass.WARNING.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.WARNING.value());
	}
	
	/**
	 * IziToast warning message (without title) using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast warning(final String message, final EIziToastPosition iziToastPosition, final String warningIcon) {
		IconBean.warningInstance(warningIcon);
		return new IziToast(ENotificationType.WARNING.value(), null, message, IziToastCssClass.WARNING.value, iziToastPosition.value(), CustomIcon.WARNING.value());
	}

	/**
	 * IziToast warning message with title using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast warning(final String title, final String message, final EIziToastPosition iziToastPosition, final String warningIcon) {
		IconBean.warningInstance(warningIcon);
		return new IziToast(ENotificationType.WARNING.value(), title, message, IziToastCssClass.WARNING.value, iziToastPosition.value(), CustomIcon.WARNING.value());
	}
	
	/**
	 * IziToast error message (without title and without icon)
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast errorSimple(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.ERROR.value(), null, message, IziToastCssClass.ERROR.value, iziToastPosition.value());
	}
	
	/**
	 * IziToast error message (without title) using FontAwesome icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast error(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.ERROR.value(), null, message, IziToastCssClass.ERROR.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.ERROR.value());
	}

	/**
	 * IziToast error message with title using FontAwesome icon
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast error(final String title, final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.ERROR.value(), title, message, IziToastCssClass.ERROR.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.ERROR.value());
	}
	
	/**
	 * IziToast error message (without title) using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast error(final String message, final EIziToastPosition iziToastPosition, final String errorIcon) {
		IconBean.errorInstance(errorIcon);
		return new IziToast(ENotificationType.ERROR.value(), null, message, IziToastCssClass.ERROR.value, iziToastPosition.value(), CustomIcon.ERROR.value());
	}

	/**
	 * IziToast error message with title using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast error(final String title, final String message, final EIziToastPosition iziToastPosition, final String errorIcon) {
		IconBean.errorInstance(errorIcon);
		return new IziToast(ENotificationType.ERROR.value(), title, message, IziToastCssClass.ERROR.value, iziToastPosition.value(), CustomIcon.ERROR.value());
	}
	
	/**
	 * IziToast dark message (without title and without icon)
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast darkSimple(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.DARK.value(), null, message, IziToastCssClass.DARK.value, iziToastPosition.value());
	}
	
	/**
	 * IziToast dark message (without title) using FontAwesome icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast dark(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.DARK.value(), null, message, IziToastCssClass.DARK.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.DARK.value());
	}

	/**
	 * IziToast error message with title using FontAwesome icon
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast dark(final String title, final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.DARK.value(), title, message, IziToastCssClass.DARK.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.DARK.value());
	}
	
	/**
	 * IziToast dark message (without title) using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast dark(final String message, final EIziToastPosition iziToastPosition, final String darkIcon) {
		IconBean.darkInstance(darkIcon);
		return new IziToast(ENotificationType.DARK.value(), null, message, IziToastCssClass.DARK.value, iziToastPosition.value(), CustomIcon.DARK.value());
	}

	/**
	 * IziToast dark message with title using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast dark(final String title, final String message, final EIziToastPosition iziToastPosition, final String darkIcon) {
		IconBean.darkInstance(darkIcon);
		return new IziToast(ENotificationType.DARK.value(), title, message, IziToastCssClass.DARK.value, iziToastPosition.value(), CustomIcon.DARK.value());
	}
	
	/**
	 * IziToastCssClass. Default CSS classes for IziToast. Has to be either SUCCESS, ERROR, INFO, WARNING or DARK
	 */
	public enum IziToastCssClass {
		SUCCESS("iziToast iziToast-success"),
		ERROR("iziToast iziToast-danger"),
		INFO("iziToast iziToast-info"),
		WARNING("iziToast iziToast-warning"),
		DARK("iziToast iziToast-dark");

		private final String value;

		IziToastCssClass(String v) {
	        value = v;
	    }

	    public String value() {
	        return value;
	    }

	    public IziToastCssClass fromValue(String v) {
	        return valueOf(v);
	    }
	}
}