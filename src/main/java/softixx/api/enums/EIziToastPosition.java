package softixx.api.enums;

/**
 * IziToastPosition. Different positions to display an IziToast notification. <br>
 * Has to be either TOP_RIGHT, TOP_LEFT, TOP_CENTER, BOTTOM_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER or CENTER
 */
public enum EIziToastPosition {
    TOP_RIGHT("topRight"),
    TOP_LEFT("topLeft"),
    TOP_CENTER("topCenter"),
    BOTTOM_RIGHT("bottomRight"),
    BOTTOM_LEFT("bottomLeft"),
    BOTTOM_CENTER("bottomCenter"),
    CENTER("center");

    private final String value;

    EIziToastPosition(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public EIziToastPosition fromValue(String v) {
        return valueOf(v);
    }
}
