package com.bootcamp.domain.spi;

import com.bootcamp.domain.model.Bootcamp;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IUserExternalServicePort {

    Mono<String> registerBootcamps(List<Bootcamp> bootcamps, String email);
}
