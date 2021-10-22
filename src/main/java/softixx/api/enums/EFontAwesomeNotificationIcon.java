package softixx.api.enums;

/**
 * IziToastFontAwesomeIcon. FontAwesome icons for IziToast notifications. <br>
 * Has to be either TOP_RIGHT, TOP_LEFT, TOP_CENTER, BOTTOM_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER or CENTER
 */
public enum EFontAwesomeNotificationIcon {
    SUCCESS("fas fa-check-circle"),
    ERROR("fas fa-exclamation-circle"),
    INFO("fas fa-info-circle"),
    WARNING("fas fa-exclamation-triangle"),
    DARK("fas fa-check");

    private final String value;

    EFontAwesomeNotificationIcon(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public EFontAwesomeNotificationIcon fromValue(String v) {
        return valueOf(v);
    }
}