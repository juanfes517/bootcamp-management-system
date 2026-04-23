package com.bootcamp.infrastructure.input.rest.controller;

import com.bootcamp.application.handler.ICapabilityHandler;
import com.bootcamp.domain.model.Capability;
import com.bootcamp.infrastructure.helper.mapper.CapabilityMapper;
import com.bootcamp.infrastructure.input.rest.dto.request.CapabilityRequestDTO;
import com.bootcamp.infrastructure.input.rest.dto.response.CapabilityResponseDTO;
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
@RequestMapping("/capability")
@RequiredArgsConstructor
public class CapabilityController {

    private final ICapabilityHandler capabilityHandler;
    private final CapabilityMapper capabilityMapper;

    @PostMapping
    public Mono<ResponseEntity<CapabilityResponseDTO>> registerCapability(
            @Valid @RequestBody CapabilityRequestDTO capabilityRequestDTO) {
        Capability capability = capabilityMapper.toDomain(capabilityRequestDTO);
        return capabilityHandler
                .registerCapability(capability, capabilityRequestDTO.getTechnologiesIds())
                .map(savedCapability -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(capabilityMapper.toResponseDTO(savedCapability)));
    }
}
