package softixx.api.util;

public class UPattern {
	
	public static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
	public static final String ALPHABETIC_PATTERN = "^[a-zA-ZáéíñóúüÁÉÍÑÓÚÜ]*$";
	public static final String ALPHABETIC_WITH_SPACE_PATTERN = "^[a-zA-ZáéíñóúüÁÉÍÑÓÚÜ\\s]*$";
	public static final String NUMBER_PATTERN = "\\d+";
	public static final String NUMBER_WITH_SPACE_PATTERN = "^[0-9\\s]*$";
	public static final String RFC_PATTERN = "[A-Z&Ñ]{3,4}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]?";
	public static final String RFC12_PATTERN = "[A-Z&Ñ]{3}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]?";
	public static final String RFC13_PATTERN = "[A-Z&Ñ]{4}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]?";
	public static final String CURP_PATTERN = "[A-Z][AEIOUX][A-Z]{2}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[MH]([ABCMTZ]S|[BCJMOT]C|[CNPST]L|[GNQ]T|[GQS]R|C[MH]|[MY]N|[DH]G|NE|VZ|DF|SP)[BCDFGHJ-NP-TV-Z]{3}[0-9A-Z][0-9]";
	public static final String STATE_MX_PATTERN = "agc|bac|bcs|cam|coa|col|chp|chh|cdmx|dur|gua|gue|hid|jal|mex|mic|mor|nay|nul|oax|pue|que|roo|slp|sin|son|tab|tam|tla|ver|yuc|zac";
	public static final String ZIP_CODE_MX = "^\\d{5}$";
	public static final String PHONE_MX = "^\\d{10}$";
	
}