package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.model.Technology;
import com.bootcamp.infrastructure.helper.mapper.TechnologyMapper;
import com.bootcamp.infrastructure.output.r2dbc.entity.TechnologyEntity;
import com.bootcamp.infrastructure.output.r2dbc.repository.ITechnologyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyR2dbcPersistenceAdapterTest {

    @InjectMocks
    private TechnologyR2dbcPersistenceAdapter adapter;

    @Mock
    private ITechnologyRepository technologyRepository;

    @Mock
    private TechnologyMapper technologyMapper;

    private static String validName;
    private static String invalidName;
    private static Technology createdTechnology;
    private static Technology newTechnology;
    private static TechnologyEntity validTechnologyEntity;
    private static TechnologyEntity technologyEntity1;
    private static TechnologyEntity technologyEntity2;
    private static Technology technology1;
    private static Technology technology2;


    @BeforeAll
    static void setUp() {

        validName = "Valid Name";
        invalidName = "Invalid Name";
        String validDescription = "Valid Description";

        newTechnology = Technology.builder()
                .id(null)
                .name(validName)
                .description(validDescription)
                .build();

        createdTechnology = Technology.builder()
                .id(1L)
                .name(validName)
                .description(validDescription)
                .build();

        validTechnologyEntity = TechnologyEntity.builder()
                .id(1L)
                .name(validName)
                .description(validDescription)
                .build();

        technologyEntity1 = TechnologyEntity.builder()
                .id(2L)
                .name(validName + "2")
                .description(validDescription)
                .build();

        technologyEntity2 = TechnologyEntity.builder()
                .id(3L)
                .name(validName + "3")
                .description(validDescription)
                .build();

        technology1 = Technology.builder()
                .id(2L)
                .name(validName + "2")
                .description(validDescription)
                .build();

        technology2 = Technology.builder()
                .id(3L)
                .name(validName + "3")
                .description(validDescription)
                .build();
    }

    @Test
    void shouldSaveTechnologySuccessfully() {
        when(technologyMapper.toEntity(any(Technology.class)))
                .thenReturn(validTechnologyEntity);

        when(technologyRepository.save(any(TechnologyEntity.class)))
                .thenReturn(Mono.just(validTechnologyEntity));

        when(technologyMapper.toDomain(any(TechnologyEntity.class)))
                .thenReturn(createdTechnology);

        StepVerifier.create(adapter.save(newTechnology))
                .expectNextMatches(technology ->
                        technology.getId().equals(createdTechnology.getId()) &&
                        technology.getName().equals(createdTechnology.getName()) &&
                        technology.getDescription().equals(createdTechnology.getDescription()))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldReturnTrueWhenTechnologyExistsByName() {
        when(technologyRepository.existsByName(validName))
                .thenReturn(Mono.just(Boolean.TRUE));

        StepVerifier.create(adapter.existsByName(validName))
                .expectNext(true)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldReturnFalseWhenTechnologyExistsByName() {
        when(technologyRepository.existsByName(invalidName))
                .thenReturn(Mono.just(Boolean.FALSE));

        StepVerifier.create(adapter.existsByName(invalidName))
                .expectNext(false)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldReturnAllTechnologies() {
        List<Long> technologiesId = List.of(2L, 3L);

        when(technologyRepository.findAllById(technologiesId))
                .thenReturn(Flux.just(technologyEntity1, technologyEntity2));
        when(technologyMapper.toDomain(technologyEntity1))
                .thenReturn(technology1);
        when(technologyMapper.toDomain(technologyEntity2))
                .thenReturn(technology2);

        StepVerifier.create(adapter.findAllByIds(technologiesId))
                .expectNextMatches(technology -> technology.getId().equals(2L) &&
                                                 technology.getName().equals(technologyEntity1.getName()) &&
                                                 technology.getDescription().equals(technologyEntity1.getDescription()))
                .expectNextMatches(technology -> technology.getId().equals(3L) &&
                                                 technology.getName().equals(technologyEntity2.getName()) &&
                                                 technology.getDescription().equals(technologyEntity2.getDescription()))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldReturnPartialTechnologies() {
        List<Long> technologiesId = List.of(2L, 99L);

        when(technologyRepository.findAllById(technologiesId))
                .thenReturn(Flux.just(technologyEntity1));
        when(technologyMapper.toDomain(technologyEntity1))
                .thenReturn(technology1);

        StepVerifier.create(adapter.findAllByIds(technologiesId))
                .expectNextMatches(technology -> technology.getId().equals(2L) &&
                                                 technology.getName().equals(technologyEntity1.getName()) &&
                                                 technology.getDescription().equals(technologyEntity1.getDescription()))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldReturnVoidTechnologies() {
        List<Long> technologiesId = List.of(100L, 99L);

        when(technologyRepository.findAllById(technologiesId))
                .thenReturn(Flux.empty());

        StepVerifier.create(adapter.findAllByIds(technologiesId))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}