package com.bootcamp.infrastructure.input.rest.dto.request;

import com.bootcamp.infrastructure.helper.constant.DtoConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CapabilityRequestDTO {

    @NotBlank(message = DtoConstants.THE_NAME_IS_REQUIRED_MESSAGE)
    private String name;

    @NotBlank(message = DtoConstants.THE_DESCRIPTION_IS_REQUIRED_MESSAGE)
    private String description;

    @NotNull(message = DtoConstants.THE_TECHNOLOGIES_IDS_IS_REQUIRED_MESSAGE)
    private List<Long> technologiesIds;
}
