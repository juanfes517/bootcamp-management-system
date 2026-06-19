package com.bootcamp.infrastructure.output.webclient.adapter;

import com.bootcamp.domain.helper.exception.DateConflictException;
import com.bootcamp.domain.helper.exception.DuplicateBootcampsException;
import com.bootcamp.domain.helper.exception.UserNotExistsException;
import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.spi.IUserExternalServicePort;
import com.bootcamp.infrastructure.input.rest.dto.response.ExceptionResponseDTO;
import com.bootcamp.infrastructure.output.webclient.dto.UserBootcampRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserWebClientAdapter implements IUserExternalServicePort {

    private final WebClient userServiceWebClient;

    @Override
    public Mono<String> registerBootcamps(List<Bootcamp> bootcamps, String email) {
        return userServiceWebClient.post()
                .uri("/users/bootcamps")
                .bodyValue(new UserBootcampRequestDTO(email, bootcamps))
                .retrieve()
                .onStatus(status -> status.value() == 404, response ->
                        Mono.error(new UserNotExistsException(email)))
                .onStatus(status -> status.value() == 409, response ->
                        response.bodyToMono(ExceptionResponseDTO.class)
                                .flatMap(ex -> Mono.error(new DuplicateBootcampsException(
                                        ex.getMessage(), ex.getDetails()))))
                .onStatus(status -> status.value() == 400, response ->
                        response.bodyToMono(ExceptionResponseDTO.class)
                                .flatMap(ex -> Mono.error(new DateConflictException(ex.getDetails()))))
                .bodyToMono(String.class);
    }
}
