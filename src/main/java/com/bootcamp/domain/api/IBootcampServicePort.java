package com.bootcamp.domain.api;

import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.domain.model.RegistrationResult;
import com.bootcamp.domain.model.UserBootcampRegistration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcampServicePort {

    Mono<Bootcamp> registerBootcamp(Bootcamp bootcamp);
    Flux<Bootcamp> getAllBootcamps(PageRequest pageRequest);
    Flux<RegistrationResult> registerUser(UserBootcampRegistration userBootcampRegistration);
}
