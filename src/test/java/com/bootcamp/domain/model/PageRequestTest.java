package com.bootcamp.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PageRequestTest {

    private PageRequest pageRequest;

    @Test
    void shouldReturnNullWhenValueIsNullInSortBy() {
        String value = null;
        PageRequest.SortBy result = PageRequest.SortBy.fromString(value);
        assertNull(result);
    }

    @Test
    void shouldReturnNullWhenValueIsBlankInSortBy() {
        String value = "";
        PageRequest.SortBy result = PageRequest.SortBy.fromString(value);
        assertNull(result);
    }

    @Test
    void shouldReturnNameWhenValueIsName() {
        String value = "name";
        PageRequest.SortBy result = PageRequest.SortBy.fromString(value);
        assertEquals(PageRequest.SortBy.NAME, result);
    }

    @Test
    void shouldReturnTechnologyCountWhenValueIsTechnologyCount() {
        String value = "technologyCount";
        PageRequest.SortBy result = PageRequest.SortBy.fromString(value);
        assertEquals(PageRequest.SortBy.TECHNOLOGY_COUNT, result);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenValueIsInvalidInSortBy() {
        String value = "invalid value";
        IllegalArgumentException result = assertThrows(
                IllegalArgumentException.class,
                () -> PageRequest.SortBy.fromString(value));
        assertEquals("Invalid sortBy value: " + value, result.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"asc", "ASC", "Asc"})
    void shouldReturnAscWhenValueIsNullEmptyOrAsc(String value) {
        PageRequest.SortOrder result = PageRequest.SortOrder.fromString(value);
        assertEquals(PageRequest.SortOrder.ASC, result);
    }

    @Test
    void shouldReturnDescWhenValueIsDesc() {
        String value = "desc";
        PageRequest.SortOrder result = PageRequest.SortOrder.fromString(value);
        assertEquals(PageRequest.SortOrder.DESC, result);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenValueIsInvalidInSortOrder() {
        String value = "invalid value";
        IllegalArgumentException result = assertThrows(
                IllegalArgumentException.class,
                () -> PageRequest.SortOrder.fromString(value));
        assertEquals("Invalid sortOrder value: " + value, result.getMessage());
    }
}