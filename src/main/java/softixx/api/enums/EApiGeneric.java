package softixx.api.enums;

public enum EApiGeneric {
	NULL(null),
	FORBIDDEN_REQUEST_EXCEPTION("ForbiddenRequestException"),
	MAPPED_EXCEPTION("MappedException"),
	DATA_INTEGRITY_VIOLATION_EXCEPTION("DataIntegrityViolationException");

	private final String value;

	EApiGeneric(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public EApiGeneric fromValue(String v) {
        return valueOf(v);
    }
}