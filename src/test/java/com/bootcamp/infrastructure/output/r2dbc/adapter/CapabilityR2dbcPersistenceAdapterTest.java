package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.infrastructure.helper.mapper.CapabilityMapper;
import com.bootcamp.infrastructure.output.r2dbc.entity.CapabilityEntity;
import com.bootcamp.infrastructure.output.r2dbc.repository.ICapabilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CapabilityR2dbcPersistenceAdapterTest {

    @InjectMocks
    private CapabilityR2dbcPersistenceAdapter adapter;

    @Mock
    private ICapabilityRepository capabilityRepository;

    @Mock
    private CapabilityMapper capabilityMapper;

    private static Capability newCapability;
    private static Capability capability;
    private static CapabilityEntity newCapabilityEntity;
    private static CapabilityEntity capabilityEntity;

    @BeforeEach
    void setUp() {
        newCapability = Capability.builder()
                .name("Capability name")
                .description("Capability description")
                .build();
        capability = Capability.builder()
                .id(1L)
                .name("Capability name")
                .description("Capability description")
                .build();
        newCapabilityEntity = CapabilityEntity.builder()
                .name("Capability name")
                .description("Capability description")
                .build();
        capabilityEntity = CapabilityEntity.builder()
                .id(1L)
                .name("Capability name")
                .description("Capability description")
                .build();
    }

    @Test
    void shouldSaveCapabilitySuccessfully() {
        when(capabilityMapper.toEntity(newCapability))
                .thenReturn(newCapabilityEntity);
        when(capabilityMapper.toDomain(capabilityEntity))
                .thenReturn(capability);
        when(capabilityRepository.save(newCapabilityEntity))
                .thenReturn(Mono.just(capabilityEntity));

        StepVerifier.create(adapter.save(newCapability))
                .expectNextMatches(savedCapability -> savedCapability.getId().equals(1L)
                                                      && savedCapability.getName().equals("Capability name")
                                                      && savedCapability.getDescription().equals("Capability description"))
                .expectComplete()
                .verify();

    }
}