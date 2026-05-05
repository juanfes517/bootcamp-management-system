package com.bootcamp.infrastructure.input.rest.dto.request;

import com.bootcamp.infrastructure.helper.constant.DtoConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class BootcampRequestDTO {

    @NotBlank(message = DtoConstants.THE_NAME_IS_REQUIRED_MESSAGE)
    private String name;

    @NotBlank(message = DtoConstants.THE_DESCRIPTION_IS_REQUIRED_MESSAGE)
    private String description;

    @NotNull(message = DtoConstants.THE_RELEASE_DATE_IS_REQUIRED_MESSAGE)
    private LocalDateTime releaseDate;

    @NotNull(message = DtoConstants.THE_DURATION_IS_REQUIRED_MESSAGE)
    private Integer durationDays;

    @NotNull(message = DtoConstants.THE_CAPABILITIES_IDS_IS_REQUIRED_MESSAGE)
    private List<Long> capabilitiesIds;
}
