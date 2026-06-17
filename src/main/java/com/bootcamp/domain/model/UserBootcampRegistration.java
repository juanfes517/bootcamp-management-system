package com.bootcamp.domain.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserBootcampRegistration {

    private String email;
    private List<Long> bootcampIds;
}
