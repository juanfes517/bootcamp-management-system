package com.bootcamp.infrastructure.config;

import com.bootcamp.domain.api.*;
import com.bootcamp.domain.spi.*;
import com.bootcamp.domain.usecase.*;
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

    @Bean
    public IBootcampServicePort bootcampServicePort(IBootcampPersistencePort bootcampPersistencePort) {
        return new BootcampUseCase(bootcampPersistencePort);
    }

    @Bean
    public ICapabilityBootcampServicePort capabilityBootcampServicePort(
            ICapabilityBootcampPersistencePort capabilityBootcampPersistencePort) {
        return new CapabilityBootcampUseCase(capabilityBootcampPersistencePort);
    }
}
