package softixx.api.enums;

public enum EAction {
	//##### VISUALIZAR
	VISUALIZATION("RESTRICTED"),

	//##### VISUALIZAR, REGISTRAR, MODIFICAR
	CONFIG("CONFIG"),

	//##### VISUALIZAR, REGISTRAR, MODIFICAR, ELIMINAR
	FULL("FULL");

	private final String value;

	EAction(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public EAction fromValue(String v) {
		return valueOf(v);
	}
}