package com.bootcamp.domain.usecase;

import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.domain.spi.IBootcampPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampUseCaseTest {

    @InjectMocks
    private BootcampUseCase bootcampUseCase;

    @Mock
    private IBootcampPersistencePort bootcampPersistencePort;

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

        when(bootcampPersistencePort.save(newBootcamp))
                .thenReturn(Mono.just(savedBootcamp));

        StepVerifier.create(bootcampUseCase.registerBootcamp(newBootcamp))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(savedBootcamp.getId()) &&
                                               bootcamp.getName().equals(savedBootcamp.getName()) &&
                                               bootcamp.getDescription().equals(savedBootcamp.getDescription()) &&
                                               bootcamp.getReleaseDate().equals(savedBootcamp.getReleaseDate()) &&
                                               bootcamp.getDurationDays() == savedBootcamp.getDurationDays())
                .verifyComplete();
    }

    @Test
    void shouldGetAllBootcampsSuccessfully() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(2)
                .sortBy("name")
                .sortOrder(PageRequest.SortOrder.DESC)
                .build();

        Bootcamp bootcamp1 = Bootcamp.builder()
                .id(1L)
                .name("Bootcamp 1")
                .description("Description 1")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .capabilityList(List.of(new Capability(), new Capability()))
                .build();

        Bootcamp bootcamp2 = Bootcamp.builder()
                .id(1L)
                .name("Bootcamp 2")
                .description("Description 2")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .capabilityList(List.of(new Capability(), new Capability()))
                .build();

        Flux<Bootcamp> bootcamps = Flux.just(bootcamp1, bootcamp2);

        when(bootcampPersistencePort.findAll(pageRequest))
                .thenReturn(bootcamps);

        StepVerifier.create(bootcampUseCase.getAllBootcamps(pageRequest))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(bootcamp1.getId()))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(bootcamp2.getId()))
                .verifyComplete();
    }
}