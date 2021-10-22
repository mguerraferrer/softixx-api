package softixx.api.enums;

/**
 * Notification types. <br> 
 * There are four types: <b>SUCCESS</b>, <b>INFO</b>, <b>WARNING</b> and <b>ERROR</b>
 */
public enum ENotificationType {
    SUCCESS("success"),
    ERROR("error"),
    INFO("info"),
    WARNING("warning"),
    DARK("dark");

    private final String value;

    ENotificationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public ENotificationType fromValue(String v) {
        return valueOf(v);
    }
}
