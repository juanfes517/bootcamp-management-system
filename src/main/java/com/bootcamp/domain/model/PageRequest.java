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
    private String sortBy;
    private SortOrder sortOrder;

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
