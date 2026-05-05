package com.bootcamp.application.handler.impl;

import com.bootcamp.domain.api.IBootcampServicePort;
import com.bootcamp.domain.api.ICapabilityBootcampServicePort;
import com.bootcamp.domain.api.ICapabilityServicePort;
import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.Technology;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
}