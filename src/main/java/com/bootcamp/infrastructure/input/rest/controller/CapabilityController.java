package com.bootcamp.infrastructure.input.rest.controller;

import com.bootcamp.application.handler.ICapabilityHandler;
import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.infrastructure.helper.constant.ApiConstants;
import com.bootcamp.infrastructure.helper.mapper.CapabilityMapper;
import com.bootcamp.infrastructure.input.rest.dto.request.CapabilityRequestDTO;
import com.bootcamp.infrastructure.input.rest.dto.response.CapabilityResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
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

    @GetMapping
    public Flux<CapabilityResponseDTO> getAllCapabilities(
            @RequestParam(defaultValue = ApiConstants.DEFAULT_PAGE) @Min(0) int page,
            @RequestParam(defaultValue = ApiConstants.DEFAULT_SIZE) @Min(1) int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        PageRequest pageRequest = PageRequest.builder()
                .page(page)
                .size(size)
                .sortBy(PageRequest.SortBy.fromString(sortBy))
                .sortOrder(PageRequest.SortOrder.fromString(sortOrder))
                .build();
        return capabilityHandler.getAllCapabilities(pageRequest)
                .map(capabilityMapper::toResponseDTO);
    }
}
