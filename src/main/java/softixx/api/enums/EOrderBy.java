package softixx.api.enums;

import java.util.Locale;
import java.util.Optional;

public enum EOrderBy {
    ASC, DESC;

    public static EOrderBy fromString(String value) {

        try {
            return EOrderBy.valueOf(value.toUpperCase(Locale.US));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(
                    "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
        }
    }

    /**
     * Returns the {@link EOrderBy} enum for the given {@link String} or null if it cannot be parsed into an enum
     * value.
     *
     * @param value
     * @return
     */
    public static Optional<EOrderBy> fromOptionalString(String value) {

        try {
            return Optional.of(fromString(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
