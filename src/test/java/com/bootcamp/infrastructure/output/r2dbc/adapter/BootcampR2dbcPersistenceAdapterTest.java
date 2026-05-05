package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.infrastructure.helper.mapper.BootcampMapper;
import com.bootcamp.infrastructure.output.r2dbc.entity.BootcampEntity;
import com.bootcamp.infrastructure.output.r2dbc.repository.IBootcampRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampR2dbcPersistenceAdapterTest {

    @InjectMocks
    private BootcampR2dbcPersistenceAdapter adapter;

    @Mock
    private IBootcampRepository bootcampRepository;

    @Mock
    private BootcampMapper bootcampMapper;

    @Test
    void shouldSaveBootcampSuccessfully() {
        Bootcamp newBootcamp = Bootcamp.builder()
                .name("Bootcamp name")
                .description("Bootcamp description")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .build();

        Bootcamp savedBootcamp = Bootcamp.builder()
                .id(1L)
                .name("Bootcamp name")
                .description("Bootcamp description")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .build();

        BootcampEntity newBootcampEntity = BootcampEntity.builder()
                .name("Bootcamp name")
                .description("Bootcamp description")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .build();

        BootcampEntity savedBootcampEntity = BootcampEntity.builder()
                .id(1L)
                .name("Bootcamp name")
                .description("Bootcamp description")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .build();

        when(bootcampMapper.toEntity(newBootcamp))
                .thenReturn(newBootcampEntity);
        when(bootcampMapper.toDomain(savedBootcampEntity))
                .thenReturn(savedBootcamp);
        when(bootcampRepository.save(newBootcampEntity))
                .thenReturn(Mono.just(savedBootcampEntity));

        StepVerifier.create(adapter.save(newBootcamp))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(savedBootcamp.getId()))
                .verifyComplete();
    }
}