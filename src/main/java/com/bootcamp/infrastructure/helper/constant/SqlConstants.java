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
            SELECT c.id AS capability_id, c.name AS capability_name, c.description AS capability_description,
                    t.id AS technology_id, t.name AS technology_name, t.description AS technology_description
            FROM capability c
            LEFT JOIN technology_capability tc ON c.id = tc.capability_id
            LEFT JOIN technology t ON tc.technology_id = t.id
            WHERE c.id IN (:ids)
            """;

    public static final String FIND_ALL_BOOTCAMPS = """
            SELECT b.id AS bootcamp_id, b.name AS bootcamp_name, b.description AS bootcamp_description, b.release_date AS bootcamp_release_date, b.duration_days AS bootcamp_duration_days,
                c.id AS capability_id, c.name AS capability_name, c.description AS capability_description,
                t.id AS technology_id, t.name AS technology_name, t.description AS technology_description
            FROM (
                SELECT b.*
                FROM bootcamp b
                LIMIT :size OFFSET :offset
            ) b
            LEFT JOIN capability_bootcamp cb ON b.id = cb.bootcamp_id
            LEFT JOIN capability c ON cb.capability_id = c.id
            LEFT JOIN technology_capability tc ON c.id = tc.capability_id
            LEFT JOIN technology t ON tc.technology_id = t.id
            ORDER BY b.id, c.id, t.id
            """;

    public static final String FIND_ALL_BOOTCAMPS_ORDER_BY_NAME = """
            SELECT b.id AS bootcamp_id, b.name AS bootcamp_name, b.description AS bootcamp_description, b.release_date AS bootcamp_release_date, b.duration_days AS bootcamp_duration_days,
                c.id AS capability_id, c.name AS capability_name, c.description AS capability_description,
                t.id AS technology_id, t.name AS technology_name, t.description AS technology_description
            FROM (
                SELECT b.*
                FROM bootcamp b
                ORDER BY b.name %s
                LIMIT :size OFFSET :offset
            ) b
            LEFT JOIN capability_bootcamp cb ON b.id = cb.bootcamp_id
            LEFT JOIN capability c ON cb.capability_id = c.id
            LEFT JOIN technology_capability tc ON c.id = tc.capability_id
            LEFT JOIN technology t ON tc.technology_id = t.id
            ORDER BY b.name %s, c.id, t.id
            """;

    public static final String FIND_ALL_BOOTCAMPS_ORDER_BY_CAPABILITY_COUNT = """
            SELECT b.id AS bootcamp_id, b.name AS bootcamp_name, b.description AS bootcamp_description, b.release_date AS bootcamp_release_date, b.duration_days AS bootcamp_duration_days,
                c.id AS capability_id, c.name AS capability_name, c.description AS capability_description,
                t.id AS technology_id, t.name AS technology_name, t.description AS technology_description
            FROM (
                SELECT b.*, COUNT(cb.capability_id) AS capability_count
                FROM bootcamp b
                LEFT JOIN capability_bootcamp cb ON b.id = cb.bootcamp_id
                GROUP BY b.id
                ORDER BY capability_count %s
                LIMIT :size OFFSET :offset
            ) b
            LEFT JOIN capability_bootcamp cb ON b.id = cb.bootcamp_id
            LEFT JOIN capability c ON cb.capability_id = c.id
            LEFT JOIN technology_capability tc ON c.id = tc.capability_id
            LEFT JOIN technology t ON tc.technology_id = t.id
            ORDER BY b.capability_count %s, b.id, c.id, t.id
            """;

    public static final String SIZE_STRING = "size";
    public static final String OFFSET_STRING = "offset";
    public static final String CAPABILITY_ID = "capability_id";
    public static final String CAPABILITY_NAME = "capability_name";
    public static final String CAPABILITY_DESCRIPTION = "capability_description";
    public static final String TECHNOLOGY_ID = "technology_id";
    public static final String TECHNOLOGY_NAME = "technology_name";
    public static final String TECHNOLOGY_DESCRIPTION = "technology_description";
    public static final String BOOTCAMP_ID = "bootcamp_id";
    public static final String BOOTCAMP_NAME = "bootcamp_name";
    public static final String BOOTCAMP_DESCRIPTION = "bootcamp_description";
    public static final String BOOTCAMP_RELEASE_DATE = "bootcamp_release_date";
    public static final String BOOTCAMP_DURATION_DAYS = "bootcamp_duration_days";
}
