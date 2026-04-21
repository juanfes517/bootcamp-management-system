package com.bootcamp.infrastructure.input.rest.controller;

import com.bootcamp.application.handler.ITechnologyHandler;
import com.bootcamp.infrastructure.helper.mapper.TechnologyMapper;
import com.bootcamp.infrastructure.input.rest.dto.request.TechnologyRequestDTO;
import com.bootcamp.infrastructure.input.rest.dto.response.TechnologyResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/technology")
@RequiredArgsConstructor
public class TechnologyController {

    private final ITechnologyHandler technologyHandler;
    private final TechnologyMapper technologyMapper;

    @PostMapping
    public Mono<ResponseEntity<TechnologyResponseDTO>> registerTechnology(
            @Valid @RequestBody TechnologyRequestDTO technologyRequestDTO) {
        return technologyHandler
                .registerTechnology(technologyMapper.toDomain(technologyRequestDTO))
                .map(technology -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(technologyMapper.toResponseDTO(technology)));
    }
}
