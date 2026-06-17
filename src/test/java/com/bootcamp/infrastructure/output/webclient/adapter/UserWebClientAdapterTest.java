package com.bootcamp.infrastructure.output.webclient.adapter;

import com.bootcamp.domain.helper.exception.DuplicateBootcampsException;
import com.bootcamp.domain.helper.exception.UserNotExistsException;
import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.infrastructure.input.rest.dto.response.ExceptionResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

class UserWebClientAdapterTest {

    private MockWebServer mockWebServer;
    private UserWebClientAdapter adapter;
    private Bootcamp bootcamp1;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        adapter = new UserWebClientAdapter(webClient);

        bootcamp1 = Bootcamp.builder()
                .id(1L)
                .description("Description")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .build();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldRegisterBootcampsSuccessfully() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("Success message")
                .addHeader("Content-Type", "application/json"));

        StepVerifier.create(adapter.registerBootcamps(List.of(bootcamp1), "test@email.com"))
                .expectNext("Success message")
                .verifyComplete();
    }

    @Test
    void shouldThrowUserNotExistsExceptionWhen404() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404));

        StepVerifier.create(adapter.registerBootcamps(List.of(bootcamp1), "test@email.com"))
                .expectError(UserNotExistsException.class)
                .verify();
    }

    @Test
    void shouldThrowDuplicateBootcampsExceptionWhen409() throws JsonProcessingException {
        ExceptionResponseDTO errorResponse = ExceptionResponseDTO.builder()
                .message("Duplicate bootcamps")
                .statusCode("409")
                .details(Map.of("1", "Already registered"))
                .build();

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(409)
                .setBody(new ObjectMapper().writeValueAsString(errorResponse))
                .addHeader("Content-Type", "application/json"));

        StepVerifier.create(adapter.registerBootcamps(List.of(bootcamp1), "test@email.com"))
                .expectError(DuplicateBootcampsException.class)
                .verify();
    }
}