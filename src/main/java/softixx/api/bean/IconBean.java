package softixx.api.bean;

/**
 * This class has 5 different static instances: <br>
 * <b>IconBean.instance(String successIcon, String errorIcon, String infoIcon,
 * String warningIcon, String darkIcon)</b> - This instance set a value for all
 * CustomIcon (CustomIcon.SUCCESS, CustomIcon.WARNING, CustomIcon.INFO),
 * CustomIcon.ERROR, CustomIcon.DARK <br>
 * <b>IconBean.successInstance(String successIcon)</b> - This instance set a
 * value for CustomIcon.SUCCESS <br>
 * <b>IconBean.errorInstance(String errorIcon)</b> - This instance set a value
 * for CustomIcon.ERROR <br>
 * <b>IconBean.infoInstance(String infoIcon)</b> - This instance set a value for
 * CustomIcon.INFO <br>
 * <b>IconBean.warningInstance(String warningIcon)</b> - This instance set a
 * value for CustomIcon.WARNING <br>
 * <b>IconBean.darkInstance(String darkIcon)</b> - This instance set a value for
 * CustomIcon.DARK
 * 
 * @see CustomIcon
 */
public class IconBean {
	private static String success;
	private static String error;
	private static String info;
	private static String warning;
	private static String dark;

	public static void instance(final String successIcon, final String errorIcon, final String infoIcon,
			final String warningIcon, final String darkIcon) {
		success = successIcon;
		error = errorIcon;
		info = infoIcon;
		warning = warningIcon;
		dark = darkIcon;
	}

	public static void successInstance(final String successIcon) {
		success = successIcon;
	}

	public static void errorInstance(final String errorIcon) {
		error = errorIcon;
	}

	public static void infoInstance(final String infoIcon) {
		info = infoIcon;
	}

	public static void warningInstance(final String warningIcon) {
		warning = warningIcon;
	}

	public static void darkInstance(final String darkIcon) {
		dark = darkIcon;
	}

	/**
	 * CustomIcon. Custom icons for IziToast notifications. <br>
	 * Has to be either SUCCESS, ERROR, INFO, WARNING or DARK
	 * 
	 * @see IconBean
	 */
	public enum CustomIcon {
		SUCCESS(success), ERROR(error), INFO(info), WARNING(warning), DARK(dark);

		private final String value;

		CustomIcon(String v) {
			value = v;
		}

		public String value() {
			return value;
		}

		public CustomIcon fromValue(String v) {
			return valueOf(v);
		}
	}
}