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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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

    private static String emptyString;
    private static String validName;
    private static String invalidName;
    private static String validDescription;
    private static String invalidDescription;
    private static Technology createdTechnology;
    private static Technology newTechnology;
    private static TechnologyEntity validTechnologyEntity;


    @BeforeAll
    static void setUp() {

        emptyString= "";
        validName = "Valid Name";
        invalidName = "Invalid Name";
        validDescription = "Valid Description";
        invalidDescription = "Invalid Description";

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
}