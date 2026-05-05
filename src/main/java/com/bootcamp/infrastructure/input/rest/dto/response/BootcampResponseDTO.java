package com.bootcamp.infrastructure.input.rest.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class BootcampResponseDTO {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime releaseDate;
    private int durationDays;
    private List<CapabilityResponseDTO> capabilityList;
}
