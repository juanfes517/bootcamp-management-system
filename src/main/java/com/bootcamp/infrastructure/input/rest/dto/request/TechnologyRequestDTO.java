package com.bootcamp.infrastructure.input.rest.dto.request;

import com.bootcamp.infrastructure.helper.constant.DtoConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class TechnologyRequestDTO {

    @NotBlank(message = DtoConstants.THE_NAME_IS_REQUIRED_MESSAGE)
    @Size(max = DtoConstants.TECHNOLOGY_NAME_LIMIT, message = DtoConstants.NAME_IS_TOO_LONG_MESSAGE)
    private String name;

    @NotBlank(message = DtoConstants.THE_DESCRIPTION_IS_REQUIRED_MESSAGE)
    @Size(max = DtoConstants.TECHNOLOGY_DESCRIPTION_LIMIT, message = DtoConstants.DESCRIPTION_IS_TOO_LONG_MESSAGE)
    private String description;
}
