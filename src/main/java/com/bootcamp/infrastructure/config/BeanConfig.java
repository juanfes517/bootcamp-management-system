package com.bootcamp.infrastructure.config;

import com.bootcamp.domain.api.ITechnologyServicePort;
import com.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.bootcamp.domain.usecase.TechnologyUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ITechnologyServicePort technologyServicePort(ITechnologyPersistencePort technologyPersistencePort) {
        return new TechnologyUseCase(technologyPersistencePort);
    }
}
