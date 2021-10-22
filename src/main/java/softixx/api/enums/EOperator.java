package softixx.api.enums;

import java.util.Locale;
import java.util.Optional;

public enum EOperator {
    AND(" AND "),
    AND_GLOBAL(" AND ("),
    OR(" OR "),
    OR_GLOBAL(" OR ("),
    START_GLOBAL("("),
    END_GLOBAL(")"),
    END_AND(") AND "),
    END_AND_GLOBAL(") AND ("),
    END_OR(") OR "),
    END_OR_GLOBAL(") OR (");

    private String operator;

    EOperator(final String value) {
        operator = value;
    }

    public String value() {
        return operator;
    }

    public static EOperator fromString(String value) {
        try {
            return EOperator.valueOf(value.toUpperCase(Locale.US));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(
                    """
                    Invalid value '%s' for orders given! Has to be either
                    'and', 'and_global', 'or', 'or_global', 'start_global' or 'end_global' (case insensitive).
                    """, value), e);
        }
    }

    /**
     * Returns the {@link EOperator} enum for the given {@link String} or null if it cannot be parsed into an enum
     * value.
     *
     * @param value
     * @return
     */
    public static Optional<EOperator> fromOptionalString(String value) {

        try {
            return Optional.of(fromString(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
