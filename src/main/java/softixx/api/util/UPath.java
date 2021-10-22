package softixx.api.util;

public class UPath {
	public final static String ERROR_403 = "/403";
	public final static String ERROR_PATH = "/error";
	public final static String THROWABLE_PATH = "/throwable";
	public final static String UNAUTHORIZED_PATH = "/unauthorized";
	public final static String INACTIVE_SESSION_PATH = "/inactive";
	public final static String DENIED_PAGE_PATH = "/accessDenied";
	
	public static final String DEFAULT_REDIRECT_ERROR_PAGE = "redirect:/page?error";
	public static final String DEFAULT_REDIRECT_INACTIVE_SESSION = "redirect:/inactive";
	public static final String DEFAULT_REDIRECT_LOGIN_EXPIRED = "redirect:/login?expired";
	public final static String DEFAULT_REDIRECT_ACCESS_DENIED = "redirect:/accessDenied";
	public final static String DEFAULT_REDIRECT_ERROR_403 = "redirect:/403";
	
	public static final String DEFAULT_ERROR_UNAUTHORIZED_VIEW = "views/error/unauthorized";
	public static final String DEFAULT_ERROR_INACTIVE_SESSION_VIEW = "views/error/session-inactive";
	
}