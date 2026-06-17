package com.bootcamp.application.handler.impl;

import com.bootcamp.domain.api.IBootcampServicePort;
import com.bootcamp.domain.api.ICapabilityBootcampServicePort;
import com.bootcamp.domain.api.ICapabilityServicePort;
import com.bootcamp.domain.model.*;
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
class BootcampHandlerTest {

    @InjectMocks
    private BootcampHandler bootcampHandler;

    @Mock
    private IBootcampServicePort bootcampService;

    @Mock
    private ICapabilityBootcampServicePort capabilityBootcampService;

    @Mock
    private ICapabilityServicePort capabilityService;

    @Test
    void shouldRegisterBootcampSuccessfully() {
        Technology tech1 = Technology.builder()
                .id(1L)
                .build();

        Technology tech2 = Technology.builder()
                .id(2L)
                .build();

        Technology tech3 = Technology.builder()
                .id(3L)
                .build();

        Capability capability = Capability.builder()
                .id(1L)
                .technologyList(List.of(tech1, tech2, tech3))
                .build();

        Bootcamp newBootcamp = Bootcamp.builder()
                .name("Bootcamp name")
                .description("Bootcamp description")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .build();

        Bootcamp createdBootcamp = Bootcamp.builder()
                .id(1L)
                .name("Bootcamp name")
                .description("Bootcamp description")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .build();

        List<Long> capabilityIds = List.of(1L);

        when(capabilityService.validateBootcampCapabilities(capabilityIds))
                .thenReturn(Mono.just(List.of(capability)));
        when(bootcampService.registerBootcamp(newBootcamp))
                .thenReturn(Mono.just(createdBootcamp));
        when(capabilityBootcampService.createCapabilityBootcamp(createdBootcamp.getId(), capabilityIds))
                .thenReturn(Mono.empty());

        StepVerifier.create(bootcampHandler.registerBootcamp(newBootcamp, capabilityIds))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(createdBootcamp.getId()) &&
                                               bootcamp.getName().equals(createdBootcamp.getName()) &&
                                               bootcamp.getDescription().equals(createdBootcamp.getDescription()) &&
                                               bootcamp.getReleaseDate().equals(createdBootcamp.getReleaseDate()) &&
                                               bootcamp.getDurationDays() == createdBootcamp.getDurationDays() &&
                                               bootcamp.getCapabilityList().get(0).getId().equals(capability.getId()))
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

        when(bootcampService.getAllBootcamps(pageRequest))
                .thenReturn(bootcamps);

        StepVerifier.create(bootcampHandler.getAllBootcamps(pageRequest))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(bootcamp1.getId()))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(bootcamp2.getId()))
                .verifyComplete();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        UserBootcampRegistration userBootcampRegistration = UserBootcampRegistration.builder()
                .email("test@email.com")
                .bootcampIds(List.of(1L, 2L))
                .build();

        RegistrationResult registrationResult1 = RegistrationResult.builder()
                .bootcamp(Bootcamp.builder().id(1L).build())
                .success(true)
                .errorMessage(null)
                .build();

        RegistrationResult registrationResult2 = RegistrationResult.builder()
                .bootcamp(Bootcamp.builder().id(2L).build())
                .success(true)
                .errorMessage(null)
                .build();

        when(bootcampService.registerUser(userBootcampRegistration))
                .thenReturn(Flux.just(registrationResult1, registrationResult2));

        StepVerifier.create(bootcampHandler.registerUser(userBootcampRegistration))
                .expectNextMatches(result -> ((Bootcamp) result.getBootcamp()).getId() == 1L)
                .expectNextMatches(result -> ((Bootcamp) result.getBootcamp()).getId() == 2L)
                .verifyComplete();

    }
}