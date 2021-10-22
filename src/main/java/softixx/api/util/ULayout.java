package softixx.api.util;

public class ULayout {

	public enum Layout {
		DEFAULT("layouts/layout-default"),
		DEFAULT_CLEAR("layouts/layout-default"),
		WIZARD("layouts/layout-wizard"),
		PUBLIC("layouts/layout-public"),
		ROOT("layouts/layout-root");
		
		private final String value;

		Layout(String v) {
	        value = v;
	    }

	    public String value() {
	        return value;
	    }

	    public Layout fromValue(String v) {
	        return valueOf(v);
	    }
	}
	
}