package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.infrastructure.helper.constant.SqlConstants;
import com.bootcamp.infrastructure.helper.mapper.CapabilityMapper;
import com.bootcamp.infrastructure.output.r2dbc.entity.CapabilityEntity;
import com.bootcamp.infrastructure.output.r2dbc.repository.ICapabilityRepository;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CapabilityR2dbcPersistenceAdapterTest {

    @InjectMocks
    private CapabilityR2dbcPersistenceAdapter adapter;

    @Mock
    private ICapabilityRepository capabilityRepository;

    @Mock
    private CapabilityMapper capabilityMapper;

    @Mock
    private DatabaseClient databaseClient;

    @Mock
    private DatabaseClient.GenericExecuteSpec executeSpec;

    @Mock
    private FetchSpec<Map<String, Object>> fetchSpec;

    private static Capability newCapability;
    private static Capability capability;
    private static CapabilityEntity newCapabilityEntity;
    private static CapabilityEntity capabilityEntity;
    private static Map<String, Object> capability1Row1;
    private static Map<String, Object> capability1Row2;
    private static Map<String, Object> capability2Row3;
    private static Map<String, Object> capability3Row4;
    private static Map<String, Object> capability3Row5;
    private static Map<String, Object> capability3Row6;

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

        capability1Row1 = new HashMap<>();
        capability1Row1.put(SqlConstants.CAPABILITY_ID, 1L);
        capability1Row1.put(SqlConstants.CAPABILITY_NAME, "B capability");
        capability1Row1.put(SqlConstants.CAPABILITY_DESCRIPTION, null);
        capability1Row1.put(SqlConstants.TECHNOLOGY_ID, 1L);
        capability1Row1.put(SqlConstants.TECHNOLOGY_NAME, null);
        capability1Row1.put(SqlConstants.TECHNOLOGY_DESCRIPTION, null);

        capability1Row2 = new HashMap<>();
        capability1Row2.put(SqlConstants.CAPABILITY_ID, 1L);
        capability1Row2.put(SqlConstants.CAPABILITY_NAME, "B capability");
        capability1Row2.put(SqlConstants.CAPABILITY_DESCRIPTION, null);
        capability1Row2.put(SqlConstants.TECHNOLOGY_ID, 2L);
        capability1Row2.put(SqlConstants.TECHNOLOGY_NAME, null);
        capability1Row2.put(SqlConstants.TECHNOLOGY_DESCRIPTION, null);

        capability2Row3 = new HashMap<>();
        capability2Row3.put(SqlConstants.CAPABILITY_ID, 2L);
        capability2Row3.put(SqlConstants.CAPABILITY_NAME, "A capability");
        capability2Row3.put(SqlConstants.CAPABILITY_DESCRIPTION, null);
        capability2Row3.put(SqlConstants.TECHNOLOGY_ID, null);
        capability2Row3.put(SqlConstants.TECHNOLOGY_NAME, null);
        capability2Row3.put(SqlConstants.TECHNOLOGY_DESCRIPTION, null);

        capability3Row4 = new HashMap<>();
        capability3Row4.put(SqlConstants.CAPABILITY_ID, 3L);
        capability3Row4.put(SqlConstants.CAPABILITY_NAME, "C capability");
        capability3Row4.put(SqlConstants.CAPABILITY_DESCRIPTION, null);
        capability3Row4.put(SqlConstants.TECHNOLOGY_ID, 1L);
        capability3Row4.put(SqlConstants.TECHNOLOGY_NAME, null);
        capability3Row4.put(SqlConstants.TECHNOLOGY_DESCRIPTION, null);

        capability3Row5 = new HashMap<>();
        capability3Row5.put(SqlConstants.CAPABILITY_ID, 3L);
        capability3Row5.put(SqlConstants.CAPABILITY_NAME, "C capability");
        capability3Row5.put(SqlConstants.CAPABILITY_DESCRIPTION, null);
        capability3Row5.put(SqlConstants.TECHNOLOGY_ID, 2L);
        capability3Row5.put(SqlConstants.TECHNOLOGY_NAME, null);
        capability3Row5.put(SqlConstants.TECHNOLOGY_DESCRIPTION, null);

        capability3Row6 = new HashMap<>();
        capability3Row6.put(SqlConstants.CAPABILITY_ID, 3L);
        capability3Row6.put(SqlConstants.CAPABILITY_NAME, "C capability");
        capability3Row6.put(SqlConstants.CAPABILITY_DESCRIPTION, null);
        capability3Row6.put(SqlConstants.TECHNOLOGY_ID, 3L);
        capability3Row6.put(SqlConstants.TECHNOLOGY_NAME, null);
        capability3Row6.put(SqlConstants.TECHNOLOGY_DESCRIPTION, null);
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

    @Test
    void shouldGetAllCapabilitiesWhenSortByIsNull() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(3)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(
                capability1Row1, capability1Row2, capability2Row3, capability3Row4, capability3Row5, capability3Row6));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(c -> c.getId().equals(1L) &&
                                        c.getTechnologyList().size() == 2 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L))
                .expectNextMatches(c -> c.getId().equals(2L) &&
                                        c.getTechnologyList().isEmpty())
                .expectNextMatches(c -> c.getId().equals(3L) &&
                                        c.getTechnologyList().size() == 3 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L) &&
                                        c.getTechnologyList().get(2).getId().equals(3L))
                .verifyComplete();

        verify(databaseClient).sql(startsWith("SELECT c.id"));
    }

    @Test
    void shouldGetAllCapabilitiesWhenSortByIsOtherValue() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(3)
                .sortBy("Other")
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(
                capability1Row1, capability1Row2, capability2Row3, capability3Row4, capability3Row5, capability3Row6));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(c -> c.getId().equals(1L) &&
                                        c.getTechnologyList().size() == 2 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L))
                .expectNextMatches(c -> c.getId().equals(2L) &&
                                        c.getTechnologyList().isEmpty())
                .expectNextMatches(c -> c.getId().equals(3L) &&
                                        c.getTechnologyList().size() == 3 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L) &&
                                        c.getTechnologyList().get(2).getId().equals(3L))
                .verifyComplete();

        verify(databaseClient).sql(startsWith("SELECT c.id"));
    }

    @Test
    void shouldReturnOneCapabilityWithoutTechnologies() {
        PageRequest pageRequest = PageRequest.builder()
                .page(1)
                .size(1)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(capability2Row3));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(c -> c.getId().equals(2L) &&
                                        c.getTechnologyList().isEmpty())
                .verifyComplete();

        verify(databaseClient).sql(startsWith("SELECT c.id"));
    }

    @Test
    void shouldGetAllCapabilitiesWhenSortByNameAsc() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(3)
                .sortBy("name")
                .sortOrder(PageRequest.SortOrder.ASC)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(
                capability2Row3, capability1Row1, capability1Row2, capability3Row4, capability3Row5, capability3Row6));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(c -> c.getId().equals(2L) &&
                                        c.getTechnologyList().isEmpty())
                .expectNextMatches(c -> c.getId().equals(1L) &&
                                        c.getTechnologyList().size() == 2 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L))
                .expectNextMatches(c -> c.getId().equals(3L) &&
                                        c.getTechnologyList().size() == 3 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L) &&
                                        c.getTechnologyList().get(2).getId().equals(3L))
                .verifyComplete();

        verify(databaseClient).sql(contains("ORDER BY c.name ASC"));
    }

    @Test
    void shouldGetAllCapabilitiesWhenSortByNameDesc() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(3)
                .sortBy("name")
                .sortOrder(PageRequest.SortOrder.DESC)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(
                capability3Row4, capability3Row5, capability3Row6, capability1Row1, capability1Row2, capability2Row3));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(c -> c.getId().equals(3L) &&
                                        c.getTechnologyList().size() == 3 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L) &&
                                        c.getTechnologyList().get(2).getId().equals(3L))
                .expectNextMatches(c -> c.getId().equals(1L) &&
                                        c.getTechnologyList().size() == 2 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L))
                .expectNextMatches(c -> c.getId().equals(2L) &&
                                        c.getTechnologyList().isEmpty())
                .verifyComplete();

        verify(databaseClient).sql(contains("ORDER BY c.name DESC"));
    }

    @Test
    void shouldGetAllCapabilitiesWhenSortByTechnologyCountAsc() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(3)
                .sortBy("technology_count")
                .sortOrder(PageRequest.SortOrder.ASC)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(
                capability2Row3, capability1Row1, capability1Row2, capability3Row4, capability3Row5, capability3Row6));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(c -> c.getId().equals(2L) &&
                                        c.getTechnologyList().isEmpty())
                .expectNextMatches(c -> c.getId().equals(1L) &&
                                        c.getTechnologyList().size() == 2 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L))
                .expectNextMatches(c -> c.getId().equals(3L) &&
                                        c.getTechnologyList().size() == 3 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L) &&
                                        c.getTechnologyList().get(2).getId().equals(3L))
                .verifyComplete();

        verify(databaseClient).sql(contains("ORDER BY technology_count ASC"));
    }

    @Test
    void shouldGetAllCapabilitiesWhenSortByTechnologyCountDesc() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(3)
                .sortBy("technology_count")
                .sortOrder(PageRequest.SortOrder.DESC)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(
                capability3Row4, capability3Row5, capability3Row6, capability1Row1, capability1Row2, capability2Row3));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(c -> c.getId().equals(3L) &&
                                        c.getTechnologyList().size() == 3 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L) &&
                                        c.getTechnologyList().get(2).getId().equals(3L))
                .expectNextMatches(c -> c.getId().equals(1L) &&
                                        c.getTechnologyList().size() == 2 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L))
                .expectNextMatches(c -> c.getId().equals(2L) &&
                                        c.getTechnologyList().isEmpty())
                .verifyComplete();

        verify(databaseClient).sql(contains("ORDER BY technology_count DESC"));
    }

    @Test
    void shouldGetCapabilitiesWithCorrectOffset() {
        PageRequest pageRequest = PageRequest.builder()
                .page(2)
                .size(3)
                .build();

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(
                capability3Row4, capability3Row5, capability3Row6));

        StepVerifier.create(adapter.findAll(pageRequest))
                .expectNextMatches(c -> c.getId().equals(3L) &&
                                        c.getTechnologyList().size() == 3 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L) &&
                                        c.getTechnologyList().get(2).getId().equals(3L))
                .verifyComplete();

        verify(executeSpec).bind("offset", 6);
    }

    @Test
    void shouldFindAllCapabilitiesByIdsWithTechnologiesSuccessfully() {
        List<Long> capabilitiesIds = List.of(1L, 3L);

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.fetch()).thenReturn(fetchSpec);
        when(fetchSpec.all()).thenReturn(Flux.just(
                capability1Row1, capability1Row2, capability3Row4, capability3Row5, capability3Row6));

        StepVerifier.create(adapter.findAllByIdsWithTechnologies(capabilitiesIds))
                .expectNextMatches(c -> c.getId().equals(1L) &&
                                        c.getTechnologyList().size() == 2 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L))
                .expectNextMatches(c -> c.getId().equals(3L) &&
                                        c.getTechnologyList().size() == 3 &&
                                        c.getTechnologyList().get(0).getId().equals(1L) &&
                                        c.getTechnologyList().get(1).getId().equals(2L) &&
                                        c.getTechnologyList().get(2).getId().equals(3L))
                .verifyComplete();

        verify(databaseClient).sql(contains("WHERE c.id IN"));
    }
}