package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.infrastructure.output.r2dbc.entity.TechnologyCapabilityEntity;
import com.bootcamp.infrastructure.output.r2dbc.repository.ITechnologyCapabilityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyCapabilityR2dbcPersistenceAdapterTest {

    @InjectMocks
    private TechnologyCapabilityR2dbcPersistenceAdapter adapter;

    @Mock
    private ITechnologyCapabilityRepository technologyCapabilityRepository;


    private static TechnologyCapabilityEntity technologyCapabilityEntity1;
    private static TechnologyCapabilityEntity technologyCapabilityEntity2;

    @BeforeAll
    static void setUp() {
        technologyCapabilityEntity1 = TechnologyCapabilityEntity.builder()
                .id(1L)
                .capabilityId(1L)
                .technologyId(1L)
                .build();
        technologyCapabilityEntity2 = TechnologyCapabilityEntity.builder()
                .id(2L)
                .capabilityId(1L)
                .technologyId(2L)
                .build();
    }

    @Test
    void shouldSaveTechnologyCapabilitySuccessfully() {
        Long capabilityId = 1L;
        List<Long> technologiesIds = List.of(1L, 2L);

        when(technologyCapabilityRepository.save(any(TechnologyCapabilityEntity.class)))
                .thenReturn(Mono.just(technologyCapabilityEntity1))
                .thenReturn(Mono.just(technologyCapabilityEntity2));

        StepVerifier.create(adapter.save(capabilityId, technologiesIds))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}