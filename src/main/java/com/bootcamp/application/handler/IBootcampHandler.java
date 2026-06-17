package com.bootcamp.application.handler;

import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.domain.model.RegistrationResult;
import com.bootcamp.domain.model.UserBootcampRegistration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBootcampHandler {

    Mono<Bootcamp> registerBootcamp(Bootcamp bootcamp, List<Long> capabilityIds);
    Flux<Bootcamp> getAllBootcamps(PageRequest pageRequest);
    Flux<RegistrationResult> registerUser(UserBootcampRegistration userBootcampRegistration);
}
