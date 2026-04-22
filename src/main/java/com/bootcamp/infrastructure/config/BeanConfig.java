package com.bootcamp.infrastructure.config;

import com.bootcamp.domain.api.ICapabilityServicePort;
import com.bootcamp.domain.api.ITechnologyCapabilityServicePort;
import com.bootcamp.domain.api.ITechnologyServicePort;
import com.bootcamp.domain.spi.ICapabilityPersistencePort;
import com.bootcamp.domain.spi.ITechnologyCapabilityPersistencePort;
import com.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.bootcamp.domain.usecase.CapabilityUseCase;
import com.bootcamp.domain.usecase.TechnologyCapabilityUseCase;
import com.bootcamp.domain.usecase.TechnologyUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ITechnologyServicePort technologyServicePort(ITechnologyPersistencePort technologyPersistencePort) {
        return new TechnologyUseCase(technologyPersistencePort);
    }

    @Bean
    public ICapabilityServicePort capabilityServicePort(ICapabilityPersistencePort capabilityPersistencePort) {
        return new CapabilityUseCase(capabilityPersistencePort);
    }

    @Bean
    public ITechnologyCapabilityServicePort technologyCapabilityServicePort(
            ITechnologyCapabilityPersistencePort technologyCapabilityPersistencePort) {
        return new TechnologyCapabilityUseCase(technologyCapabilityPersistencePort);
    }
}
