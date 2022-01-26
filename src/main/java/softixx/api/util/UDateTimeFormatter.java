package softixx.api.util;

public class UDateTimeFormatter {
	private UDateTimeFormatter() {
		throw new IllegalStateException("Utility class");
	}
	
	/**
	 * Format: <b>dd/MM/yyyy</b>
	 */
	public static final String DATE_SIMPLE_FORMAT = "dd/MM/yyyy";
	/**
	 * Format: <b>dd.MM.yyyy</b>
	 */
	public static final String DATE_SIMPLE_DOT_FORMAT = "dd.MM.yyyy";
	/**
	 * Format: <b>yyyy-MM-dd</b>
	 */
	public static final String DATE_DB_FORMAT = "yyyy-MM-dd";
	/**
	 * Format: <b>yyyy-MM-dd HH:mm</b>
	 */
	public static final String DATE_TIME_SIMPLE_FORMAT = "yyyy-MM-dd HH:mm";
	/**
	 * Forat: <b>yyyy-MM-dd HH:mm:ss</b>
	 */
	public static final String DATE_TIME_FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * Format: <b>yyyy-MM-dd'T'HH:mm:ss</b>
	 */
	public static final String DATE_TIME_T_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	/**
	 * Format: <b>dd/MM/yyyy HH:mm:ss</b>
	 */
	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	/**
	 * Format: <b>dd/MM/yyyy hh:mm:ss a</b>
	 */
	public static final String DATE_TIME_MERIDIAN_FORMAT = "dd/MM/yyyy hh:mm:ss a";
	/**
	 * Format: <b>HH:mm:ss</b>
	 */
	public static final String TIME = "HH:mm:ss";
	/**
	 * Format: <b>hh:mm a</b>
	 */
	public static final String T12H = "hh:mm a";
	/**
	 * Format: <b>hh:mm:ss a</b>
	 */
	public static final String T12H_FULL = "hh:mm:ss a";
	/**
	 * Format: <b>HH:mm</b>
	 */
	public static final String T24H = "HH:mm";
	/**
	 * Format: <b>HH:mm:ss</b>
	 */
	public static final String T24H_FULL = "HH:mm:ss";
	
}