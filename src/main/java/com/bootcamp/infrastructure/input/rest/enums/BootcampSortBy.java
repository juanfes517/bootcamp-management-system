package com.bootcamp.infrastructure.input.rest.enums;

public enum BootcampSortBy {
    NAME,
    CAPABILITY_COUNT;

    public static BootcampSortBy fromString(String value) {
        if (value == null || value.isBlank()) return null;
        return switch (value.toLowerCase()) {
            case "name" -> NAME;
            case "capability_count" -> CAPABILITY_COUNT;
            default -> throw new IllegalArgumentException("Invalid sortBy value: " + value);
        };
    }
}
