package com.bootcamp.infrastructure.input.rest.enums;

public enum CapabilitySortBy {
    NAME,
    TECHNOLOGY_COUNT;

    public static CapabilitySortBy fromString(String value) {
        if (value == null || value.isBlank()) return null;
        return switch (value.toLowerCase()) {
            case "name" -> NAME;
            case "technology_count" -> TECHNOLOGY_COUNT;
            default -> throw new IllegalArgumentException("Invalid sortBy value: " + value);
        };
    }
}
