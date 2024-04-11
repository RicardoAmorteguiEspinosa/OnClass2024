package com.pragma.arquetipobootcamp2024.domain.util;

public final class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Field {
        NAME,
        DESCRIPTION
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description' cannot be null";
    public static final String INVALID_FIELD = "invalid field";
    public static final String FIELD_NAME_SIZE_MESSAGE = "Field 'name' must be between 2 and 50 characters";
    public static final String FIELD_DESCRIPTION_SIZE_MESSAGE = "Field 'description' must be between 2 and 90 characters";
    public static final String FIELD_TECHNOLOGIES_LIST_NULL_MESSAGE = "Field 'technologies list' cannot be null";
    public static final String FIELD_TECHNOLOGIES_LIST_SIZE_MESSAGE = "Field 'technologies list' must contain between 3 and 20 elements";
}