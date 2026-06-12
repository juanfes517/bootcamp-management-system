package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.infrastructure.helper.constant.SqlConstants;
import com.bootcamp.infrastructure.helper.mapper.BootcampMapper;
import com.bootcamp.infrastructure.output.r2dbc.entity.BootcampEntity;
import com.bootcamp.infrastructure.output.r2dbc.repository.IBootcampRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.FetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampR2dbcPersistenceAdapterTest {

    @InjectMocks
    private BootcampR2dbcPersistenceAdapter adapter;

    @Mock
    private IBootcampRepository bootcampRepository;

    @Mock
    private BootcampMapper bootcampMapper;

    @Mock
    private DatabaseClient databaseClient;

    @Mock
    private DatabaseClient.GenericExecuteSpec executeSpec;

    @Mock
    private FetchSpec<Map<String, Object>> fetchSpec;

    private Map<String, Object> bootcamp1Capability1Technology1;
    private Map<String, Object> bootcamp1Capability1Technology2;
    private Map<String, Object> bootcamp1Capability2Technology1;
    private Map<String, Object> bootcamp2Capability1Technology1;

    private static final LocalDateTime RELEASE_DATE = LocalDateTime.of(2026, 6, 1, 8, 0);

    @BeforeEach
    void setUp() {
        bootcamp1Capability1Technology1 = new HashMap<>();
        bootcamp1Capability1Technology1.put(SqlConstants.BOOTCAMP_ID, 1L);
        bootcamp1Capability1Technology1.put(SqlConstants.BOOTCAMP_NAME, "Bootcamp Java");
        bootcamp1Capability1Technology1.put(SqlConstants.BOOTCAMP_DESCRIPTION, "Descripcion bootcamp 1");
        bootcamp1Capability1Technology1.put(SqlConstants.BOOTCAMP_RELEASE_DATE, RELEASE_DATE);
        bootcamp1Capability1Technology1.put(SqlConstants.BOOTCAMP_DURATION_DAYS, 90);
        bootcamp1Capability1Technology1.put(SqlConstants.CAPABILITY_ID, 44L);
        bootcamp1Capability1Technology1.put(SqlConstants.CAPABILITY_NAME, "Backend Development");
        bootcamp1Capability1Technology1.put(SqlConstants.CAPABILITY_DESCRIPTION, "Descripcion capability 1");
        bootcamp1Capability1Technology1.put(SqlConstants.TECHNOLOGY_ID, 10L);
        bootcamp1Capability1Technology1.put(SqlConstants.TECHNOLOGY_NAME, "Java");
        bootcamp1Capability1Technology1.put(SqlConstants.TECHNOLOGY_DESCRIPTION, "Descripcion technology 1");

        bootcamp1Capability1Technology2 = new HashMap<>();
        bootcamp1Capability1Technology2.put(SqlConstants.BOOTCAMP_ID, 1L);
        bootcamp1Capability1Technology2.put(SqlConstants.BOOTCAMP_NAME, "Bootcamp Java");
        bootcamp1Capability1Technology2.put(SqlConstants.BOOTCAMP_DESCRIPTION, "Descripcion bootcamp 1");
        bootcamp1Capability1Technology2.put(SqlConstants.BOOTCAMP_RELEASE_DATE, RELEASE_DATE);
        bootcamp1Capability1Technology2.put(SqlConstants.BOOTCAMP_DURATION_DAYS, 90);
        bootcamp1Capability1Technology2.put(SqlConstants.CAPABILITY_ID, 44L);
        bootcamp1Capability1Technology2.put(SqlConstants.CAPABILITY_NAME, "Backend Development");
        bootcamp1Capability1Technology2.put(SqlConstants.CAPABILITY_DESCRIPTION, "Descripcion capability 1");
        bootcamp1Capability1Technology2.put(SqlConstants.TECHNOLOGY_ID, 12L);
        bootcamp1Capability1Technology2.put(SqlConstants.TECHNOLOGY_NAME, "JavaScript");
        bootcamp1Capability1Technology2.put(SqlConstants.TECHNOLOGY_DESCRIPTION, "Descripcion technology 2");

        bootcamp1Capability2Technology1 = new HashMap<>();
        bootcamp1Capability2Technology1.put(SqlConstants.BOOTCAMP_ID, 1L);
        bootcamp1Capability2Technology1.put(SqlConstants.BOOTCAMP_NAME, "Bootcamp Java");
        bootcamp1Capability2Technology1.put(SqlConstants.BOOTCAMP_DESCRIPTION, "Descripcion bootcamp 1");
        bootcamp1Capability2Technology1.put(SqlConstants.BOOTCAMP_RELEASE_DATE, RELEASE_DATE);
        bootcamp1Capability2Technology1.put(SqlConstants.BOOTCAMP_DURATION_DAYS, 90);
        bootcamp1Capability2Technology1.put(SqlConstants.CAPABILITY_ID, 49L);
        bootcamp1Capability2Technology1.put(SqlConstants.CAPABILITY_NAME, "Cloud Computing");
        bootcamp1Capability2Technology1.put(SqlConstants.CAPABILITY_DESCRIPTION, "Descripcion capability 2");
        bootcamp1Capability2Technology1.put(SqlConstants.TECHNOLOGY_ID, 10L);
        bootcamp1Capability2Technology1.put(SqlConstants.TECHNOLOGY_NAME, "Java");
        bootcamp1Capability2Technology1.put(SqlConstants.TECHNOLOGY_DESCRIPTION, "Descripcion technology 1");

        bootcamp2Capability1Technology1 = new HashMap<>();
        bootcamp2Capability1Technology1.put(SqlConstants.BOOTCAMP_ID, 2L);
        bootcamp2Capability1Technology1.put(SqlConstants.BOOTCAMP_NAME, "Bootcamp Data Science");
        bootcamp2Capability1Technology1.put(SqlConstants.BOOTCAMP_DESCRIPTION, "Descripcion bootcamp 2");
        bootcamp2Capability1Technology1.put(SqlConstants.BOOTCAMP_RELEASE_DATE, RELEASE_DATE);
        bootcamp2Capability1Technology1.put(SqlConstants.BOOTCAMP_DURATION_DAYS, 120);
        bootcamp2Capability1Technology1.put(SqlConstants.CAPABILITY_ID, 47L);
        bootcamp2Capability1Technology1.put(SqlConstants.CAPABILITY_NAME, "Data Science");
        bootcamp2Capability1Technology1.put(SqlConstants.CAPABILITY_DESCRIPTION, "Descripcion capability 3");
        bootcamp2Capability1Technology1.put(SqlConstants.TECHNOLOGY_ID, 11L);
        bootcamp2Capability1Technology1.put(SqlConstants.TECHNOLOGY_NAME, "Python");
        bootcamp2Capability1Technology1.put(SqlConstants.TECHNOLOGY_DESCRIPTION, "Descripcion technology 3");
    }

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

    @Test
    void shouldGetAllBootcampsWhenSortByIsNull() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(2)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(
                bootcamp1Capability1Technology1,
                bootcamp1Capability1Technology2,
                bootcamp1Capability2Technology1,
                bootcamp2Capability1Technology1));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(bootcamp ->
                        bootcamp.getId().equals(1L) &&
                        bootcamp.getCapabilityList().size() == 2 &&
                        bootcamp.getCapabilityList().stream().anyMatch(c -> c.getId().equals(44L)) &&
                        bootcamp.getCapabilityList().stream().anyMatch(c -> c.getId().equals(49L)) &&
                        bootcamp.getCapabilityList().stream()
                                .filter(c -> c.getId().equals(44L))
                                .findFirst()
                                .map(c -> c.getTechnologyList().size() == 2 &&
                                          c.getTechnologyList().stream().noneMatch(Objects::isNull) &&
                                          c.getTechnologyList().stream().anyMatch(t -> t.getId().equals(10L)) &&
                                          c.getTechnologyList().stream().anyMatch(t -> t.getId().equals(12L)))
                                .orElse(false))
                .expectNextMatches(bootcamp ->
                        bootcamp.getId().equals(2L) &&
                        bootcamp.getCapabilityList().size() == 1 &&
                        bootcamp.getCapabilityList().stream().anyMatch(c -> c.getId().equals(47L)))
                .verifyComplete();
    }

    @Test
    void shouldGetAllBootcampsOrderByNameAsc() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(2)
                .sortBy("name")
                .sortOrder(PageRequest.SortOrder.ASC)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(
                bootcamp1Capability1Technology1,
                bootcamp1Capability1Technology2,
                bootcamp1Capability2Technology1,
                bootcamp2Capability1Technology1));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(1L))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(2L))
                .verifyComplete();

        verify(databaseClient).sql(eq(SqlConstants.FIND_ALL_BOOTCAMPS_ORDER_BY_NAME
                .formatted(PageRequest.SortOrder.ASC, PageRequest.SortOrder.ASC)));
    }

    @Test
    void shouldGetAllBootcampsOrderByCapabilityCountDesc() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(2)
                .sortBy("capability_count")
                .sortOrder(PageRequest.SortOrder.DESC)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(
                bootcamp1Capability1Technology1,
                bootcamp1Capability1Technology2,
                bootcamp1Capability2Technology1,
                bootcamp2Capability1Technology1));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(1L))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(2L))
                .verifyComplete();

        verify(databaseClient).sql(eq(SqlConstants.FIND_ALL_BOOTCAMPS_ORDER_BY_CAPABILITY_COUNT
                .formatted(PageRequest.SortOrder.DESC, PageRequest.SortOrder.DESC)));
    }

    @Test
    void shouldGetAllBootcampsWithCorrectOffset() {
        PageRequest pageRequest = PageRequest.builder()
                .page(2)
                .size(3)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(bootcamp2Capability1Technology1));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(2L))
                .verifyComplete();

        verify(executeSpec).bind(SqlConstants.OFFSET_STRING, 6);
    }

    @Test
    void shouldGetAllBootcampsWhenSortByIsNullUsesCorrectQuery() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(2)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(bootcamp1Capability1Technology1));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(1L))
                .verifyComplete();

        verify(databaseClient).sql(eq(SqlConstants.FIND_ALL_BOOTCAMPS));
    }

    @Test
    void shouldIgnoreRowsWithNullCapabilityId() {
        Map<String, Object> rowWithNullCapability = new HashMap<>();
        rowWithNullCapability.put(SqlConstants.BOOTCAMP_ID, 1L);
        rowWithNullCapability.put(SqlConstants.BOOTCAMP_NAME, "Bootcamp Java");
        rowWithNullCapability.put(SqlConstants.BOOTCAMP_DESCRIPTION, "Descripcion bootcamp 1");
        rowWithNullCapability.put(SqlConstants.BOOTCAMP_RELEASE_DATE, RELEASE_DATE);
        rowWithNullCapability.put(SqlConstants.BOOTCAMP_DURATION_DAYS, 90);
        rowWithNullCapability.put(SqlConstants.CAPABILITY_ID, null); // ← capability null
        rowWithNullCapability.put(SqlConstants.CAPABILITY_NAME, null);
        rowWithNullCapability.put(SqlConstants.CAPABILITY_DESCRIPTION, null);
        rowWithNullCapability.put(SqlConstants.TECHNOLOGY_ID, null);
        rowWithNullCapability.put(SqlConstants.TECHNOLOGY_NAME, null);
        rowWithNullCapability.put(SqlConstants.TECHNOLOGY_DESCRIPTION, null);

        PageRequest pageRequest = PageRequest.builder().page(0).size(2).build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(rowWithNullCapability));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(bootcamp ->
                        bootcamp.getId().equals(1L) &&
                        bootcamp.getCapabilityList().isEmpty())
                .verifyComplete();
    }

    @Test
    void shouldIgnoreRowsWithNullTechnologyId() {
        Map<String, Object> rowWithNullTechnology = new HashMap<>();
        rowWithNullTechnology.put(SqlConstants.BOOTCAMP_ID, 1L);
        rowWithNullTechnology.put(SqlConstants.BOOTCAMP_NAME, "Bootcamp Java");
        rowWithNullTechnology.put(SqlConstants.BOOTCAMP_DESCRIPTION, "Descripcion bootcamp 1");
        rowWithNullTechnology.put(SqlConstants.BOOTCAMP_RELEASE_DATE, RELEASE_DATE);
        rowWithNullTechnology.put(SqlConstants.BOOTCAMP_DURATION_DAYS, 90);
        rowWithNullTechnology.put(SqlConstants.CAPABILITY_ID, 44L);
        rowWithNullTechnology.put(SqlConstants.CAPABILITY_NAME, "Backend Development");
        rowWithNullTechnology.put(SqlConstants.CAPABILITY_DESCRIPTION, "Descripcion capability 1");
        rowWithNullTechnology.put(SqlConstants.TECHNOLOGY_ID, null);
        rowWithNullTechnology.put(SqlConstants.TECHNOLOGY_NAME, null);
        rowWithNullTechnology.put(SqlConstants.TECHNOLOGY_DESCRIPTION, null);

        PageRequest pageRequest = PageRequest.builder().page(0).size(2).build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(rowWithNullTechnology));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(bootcamp ->
                        bootcamp.getId().equals(1L) &&
                        bootcamp.getCapabilityList().size() == 1 &&
                        bootcamp.getCapabilityList().get(0).getId().equals(44L) &&
                        bootcamp.getCapabilityList().get(0).getTechnologyList().isEmpty())
                .verifyComplete();
    }

    @Test
    void shouldUseDefaultQueryWhenSortByIsInvalid() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(2)
                .sortBy("invalid")
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(bootcamp1Capability1Technology1));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(1L))
                .verifyComplete();

        verify(databaseClient).sql(eq(SqlConstants.FIND_ALL_BOOTCAMPS));
    }
}