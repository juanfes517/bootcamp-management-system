package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.infrastructure.output.r2dbc.entity.CapabilityBootcampEntity;
import com.bootcamp.infrastructure.output.r2dbc.repository.ICapabilityBootcampRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CapabilityBootcampR2dbcPersistenceAdapterTest {

    @InjectMocks
    private CapabilityBootcampR2dbcPersistenceAdapter adapter;

    @Mock
    private ICapabilityBootcampRepository capabilityBootcampRepository;

    @Captor
    ArgumentCaptor<List<CapabilityBootcampEntity>> captor;

    @Test
    void shouldSaveCapabilityBootcampSuccessfully() {
        Long bootcampId = 1L;
        List<Long> capabilityIds = List.of(1L, 2L);

        CapabilityBootcampEntity savedEntity1 = CapabilityBootcampEntity.builder()
                .id(1L)
                .bootcampId(bootcampId)
                .capabilityId(capabilityIds.get(0))
                .build();

        CapabilityBootcampEntity savedEntity2 = CapabilityBootcampEntity.builder()
                .id(2L)
                .bootcampId(bootcampId)
                .capabilityId(capabilityIds.get(1))
                .build();

        when(capabilityBootcampRepository.saveAll(ArgumentMatchers.<Iterable<CapabilityBootcampEntity>>any()))
                .thenReturn(Flux.fromIterable(List.of(savedEntity1, savedEntity2)));

        StepVerifier.create(adapter.save(bootcampId, capabilityIds))
                .verifyComplete();

        verify(capabilityBootcampRepository).saveAll(captor.capture());

        List<CapabilityBootcampEntity> savedEntities = captor.getValue();
        assertEquals(2, savedEntities.size());
        assertTrue(savedEntities.stream().noneMatch(Objects::isNull));
        assertTrue(savedEntities.stream().allMatch(e -> e.getBootcampId().equals(bootcampId)));
        assertTrue(savedEntities.stream().allMatch(e -> capabilityIds.contains(e.getCapabilityId())));

    }

}