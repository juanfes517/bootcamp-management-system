package com.bootcamp.domain.usecase;

import com.bootcamp.domain.api.IBootcampServicePort;
import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.spi.IBootcampPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BootcampUseCase implements IBootcampServicePort {

    private final IBootcampPersistencePort bootcampPersistencePort;

    @Override
    public Mono<Bootcamp> registerBootcamp(Bootcamp bootcamp) {
        return bootcampPersistencePort.save(bootcamp);
    }
}
