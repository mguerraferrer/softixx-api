package softixx.api.enums;

import java.util.Locale;
import java.util.Optional;

public enum EConditional {
    EQUAL("=?"),
    EQUAL_NAMED_PARAM("=:"),
    EQUAL_WHERE("="),
    LIKE(" LIKE ?"),
    LIKE_NAMED_PARAM(" LIKE :"),
    LIKE_WHERE(" LIKE "),
    RLIKE(" RLIKE ^"),
    DISTINCT(" <> ?"),
    DISTINCT_NAMED_PARAM(" <> :"),
    DISTINCT_WHERE(" <> "),
    BETWEEN(" BETWEEN ? AND ?"),
    BETWEEN_NAMED_PARAM(" BETWEEN :"),
    BETWEEN_WHERE(" BETWEEN "),
    GREATER_THAN(" > ?"),
    GREATER_THAN_NAMED_PARAM(" > :"),
    GREATER_THAN_WHERE(" > "),
    GREATER_THAN_EQUAL(" >= ?"),
    GREATER_THAN_EQUAL_NAMED_PARAM(" >= :"),
    GREATER_THAN_EQUAL_WHERE(" >= "),
    LOWER(" < ?"),
    LOWER_NAMED_PARAM(" < :"),
    LOWER_WHERE(" < "),
    LOWER_THAN_EQUAL(" <= ?"),
    LOWER_THAN_EQUAL_NAMED_PARAM(" <= :"),
    LOWER_THAN_EQUAL_WHERE(" <= "),
    IS(" IS "),
    IS_NULL(" IS NULL "),
    IS_NOT(" IS NOT "),
    IS_NOT_NULL(" IS NOT NULL "),
    IN(" IN "),
    NOT_IN(" NOT IN ");

    private String conditional;

    private EConditional(final String value) {
        conditional = value;
    }

    public String value() {
        return conditional;
    }

    public static EConditional fromString(String value) {
        try {
            return EConditional.valueOf(value.toUpperCase(Locale.US));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(
            """
            Invalid value '%s' for orders given! Has to be either
            'equal', 'equal_named_param', 'like', 'like_named_param', rlike, 'distinct', 'distinct_named_param',
            'between, between_named_param', 'greater_than', 'greater_than_named_param', 'greater_or_equal_than', 
            'greater_or_equal_than_named_param', 'lower_than', 'lower_than_named_param', 'lower_or_equal_than' 
            or 'lower_or_equal_than_named_param' (case insensitive).
            """, value), e);
        }
    }

    /**
     * Returns the {@link EConditional} enum for the given {@link String} or null if it cannot be parsed into an enum
     * value.
     *
     * @param value
     * @return
     */
    public static Optional<EConditional> fromOptionalString(String value) {

        try {
            return Optional.of(fromString(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}