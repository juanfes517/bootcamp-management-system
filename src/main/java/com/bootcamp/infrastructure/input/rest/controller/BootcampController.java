package com.bootcamp.infrastructure.input.rest.controller;

import com.bootcamp.application.handler.IBootcampHandler;
import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.infrastructure.helper.mapper.BootcampMapper;
import com.bootcamp.infrastructure.input.rest.dto.request.BootcampRequestDTO;
import com.bootcamp.infrastructure.input.rest.dto.response.BootcampResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/bootcamp")
@RequiredArgsConstructor
public class BootcampController {

    private final IBootcampHandler bootcampHandler;
    private final BootcampMapper bootcampMapper;

    @PostMapping
    public Mono<ResponseEntity<BootcampResponseDTO>> registerBootcamp(
            @Valid @RequestBody BootcampRequestDTO bootcampRequestDTO
    ) {
        Bootcamp bootcamp = bootcampMapper.toDomain(bootcampRequestDTO);
        return bootcampHandler
                .registerBootcamp(bootcamp, bootcampRequestDTO.getCapabilitiesIds())
                .map(savedBootcamp -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(bootcampMapper.toResponse(savedBootcamp)));
    }
}
