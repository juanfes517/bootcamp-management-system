package com.bootcamp.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PageRequestTest {

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