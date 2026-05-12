package com.bootcamp.infrastructure.input.rest.controller;

import com.bootcamp.application.handler.IBootcampHandler;
import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.infrastructure.helper.constant.ApiConstants;
import com.bootcamp.infrastructure.helper.mapper.BootcampMapper;
import com.bootcamp.infrastructure.input.rest.dto.request.BootcampRequestDTO;
import com.bootcamp.infrastructure.input.rest.dto.response.BootcampResponseDTO;
import com.bootcamp.infrastructure.input.rest.enums.BootcampSortBy;
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

    @GetMapping
    public Flux<BootcampResponseDTO> getAllBootcamps(
            @RequestParam(defaultValue = ApiConstants.DEFAULT_PAGE) @Min(0) int page,
            @RequestParam(defaultValue = ApiConstants.DEFAULT_SIZE) @Min(1) int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {
        BootcampSortBy.fromString(sortBy);
        PageRequest pageRequest = PageRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortOrder(PageRequest.SortOrder.fromString(sortOrder))
                .build();

        return bootcampHandler
                .getAllBootcamps(pageRequest)
                .map(bootcampMapper::toResponse);
    }
}
