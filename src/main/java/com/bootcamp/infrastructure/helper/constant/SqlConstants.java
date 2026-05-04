package com.bootcamp.infrastructure.helper.constant;

public class SqlConstants {

    private SqlConstants() {
    }

    public static final String FIND_ALL_QUERY = """
            SELECT c.id AS capability_id, c.name AS capability_name, c.description AS capability_description,
                    t.id AS technology_id, t.name AS technology_name, t.description AS technology_description
            FROM (
                SELECT c.*
                FROM capability c
                LIMIT :size OFFSET :offset
            ) c
            LEFT JOIN technology_capability tc ON c.id = tc.capability_id
            LEFT JOIN technology t ON t.id = tc.technology_id
            """;

    public static final String FIND_ALL_BY_NAME_QUERY = """
            SELECT c.id AS capability_id, c.name AS capability_name, c.description AS capability_description,
                    t.id AS technology_id, t.name AS technology_name, t.description AS technology_description
            FROM (
                SELECT c.*
                FROM capability c
                ORDER BY c.name %s
                LIMIT :size OFFSET :offset
            ) c
            LEFT JOIN technology_capability tc ON c.id = tc.capability_id
            LEFT JOIN technology t ON t.id = tc.technology_id
            ORDER BY c.name %s, t.id
            """;

    public static final String FIND_ALL_BY_TECHNOLOGY_COUNT_QUERY = """
            SELECT c.id AS capability_id, c.name AS capability_name, c.description AS capability_description,
                    t.id AS technology_id, t.name AS technology_name, t.description AS technology_description
            FROM (
                SELECT c.*, COUNT(tc.technology_id) as technology_count
                FROM capability c
                LEFT JOIN technology_capability tc ON c.id = tc.capability_id
                GROUP BY c.id
                ORDER BY technology_count %s
                LIMIT :size OFFSET :offset
            ) c
            LEFT JOIN technology_capability tc ON c.id = tc.capability_id
            LEFT JOIN technology t ON t.id = tc.technology_id
            ORDER BY c.technology_count %s, c.id, t.id
            """;

    public static final String FIND_ALL_CAPABILITIES_BY_IDS = """
            SELECT c.id AS capability_id, c.name AS capability_name, c.description AS capability_description,\s
                    t.id AS technology_id, t.name AS technology_name, t.description AS technology_description
            FROM capability c
            LEFT JOIN technology_capability tc ON c.id = tc.capability_id
            LEFT JOIN technology t ON tc.technology_id = t.id
            WHERE c.id IN (:ids)
            """;

    public static final String CAPABILITY_ID = "capability_id";
    public static final String SIZE_STRING = "size";
    public static final String OFFSET_STRING = "offset";
    public static final String CAPABILITY_NAME = "capability_name";
    public static final String CAPABILITY_DESCRIPTION = "capability_description";
    public static final String TECHNOLOGY_ID = "technology_id";
    public static final String TECHNOLOGY_NAME = "technology_name";
    public static final String TECHNOLOGY_DESCRIPTION = "technology_description";
}
