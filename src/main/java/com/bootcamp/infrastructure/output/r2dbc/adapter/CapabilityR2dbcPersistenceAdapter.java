package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.domain.model.Technology;
import com.bootcamp.domain.spi.ICapabilityPersistencePort;
import com.bootcamp.infrastructure.helper.constant.SqlConstants;
import com.bootcamp.infrastructure.helper.mapper.CapabilityMapper;
import com.bootcamp.infrastructure.output.r2dbc.repository.ICapabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CapabilityR2dbcPersistenceAdapter implements ICapabilityPersistencePort {

    private final ICapabilityRepository capabilityRepository;
    private final CapabilityMapper capabilityMapper;
    private final DatabaseClient databaseClient;

    @Override
    public Mono<Capability> save(Capability capability) {
        return capabilityRepository
                .save(capabilityMapper.toEntity(capability))
                .map(capabilityMapper::toDomain);
    }

    @Override
    public Flux<Capability> findAll(PageRequest pageRequest) {
        int offset = pageRequest.getPage() * pageRequest.getSize();
        String sqlQuery = this.getQuery(pageRequest);

        return databaseClient.sql(sqlQuery)
                .bind(SqlConstants.SIZE_STRING, pageRequest.getSize())
                .bind(SqlConstants.OFFSET_STRING, offset)
                .fetch()
                .all()
                .bufferUntilChanged(row -> row.get(SqlConstants.CAPABILITY_ID))
                .map(this::buildCapability);
    }

    private String getQuery(PageRequest pageRequest) {
        PageRequest.SortBy sortBy = pageRequest.getSortBy();
        PageRequest.SortOrder sortOrder = pageRequest.getSortOrder();

        if (sortBy == null) return SqlConstants.FIND_ALL_QUERY;

        return switch (sortBy) {
            case NAME -> SqlConstants.FIND_ALL_BY_NAME_QUERY
                    .formatted(sortOrder.name(), sortOrder.name());
            case TECHNOLOGY_COUNT -> SqlConstants.FIND_ALL_BY_TECHNOLOGY_COUNT_QUERY
                    .formatted(sortOrder.name(), sortOrder.name());
        };
    }

    private Capability buildCapability(List<Map<String, Object>> rows) {
        Capability capability = Capability.builder()
                .id((Long) rows.get(0).get(SqlConstants.CAPABILITY_ID))
                .name((String) rows.get(0).get(SqlConstants.CAPABILITY_NAME))
                .description((String) rows.get(0).get(SqlConstants.CAPABILITY_DESCRIPTION))
                .build();

        List<Technology> technologies = rows.stream()
                .filter(row -> row.get(SqlConstants.TECHNOLOGY_ID) != null)
                .map(row -> Technology.builder()
                        .id((Long) row.get(SqlConstants.TECHNOLOGY_ID))
                        .name((String) row.get(SqlConstants.TECHNOLOGY_NAME))
                        .description((String) row.get(SqlConstants.TECHNOLOGY_DESCRIPTION))
                        .build())
                .toList();

        capability.setTechnologyList(technologies);
        return capability;
    }
}
