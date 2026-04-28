package com.bootcamp.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PageRequest {

    private int page;
    private int size;
    private SortBy sortBy;
    private SortOrder sortOrder;

    public enum SortBy {
        NAME, TECHNOLOGY_COUNT;

        public static SortBy fromString(String value) {
            if (value == null || value.isBlank()) return null;
            return switch (value.toLowerCase()) {
                case "name" -> NAME;
                case "technologycount" -> TECHNOLOGY_COUNT;
                default -> throw new IllegalArgumentException("Invalid sortBy value: " + value);
            };
        }
    }

    public enum SortOrder {
        ASC, DESC;

        public static SortOrder fromString(String value) {
            if (value == null || value.isBlank()) return ASC; // default ASC
            return switch (value.toUpperCase()) {
                case "ASC" -> ASC;
                case "DESC" -> DESC;
                default -> throw new IllegalArgumentException("Invalid sortOrder value: " + value);
            };
        }
    }
}
