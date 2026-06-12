package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.domain.model.Technology;
import com.bootcamp.domain.spi.IBootcampPersistencePort;
import com.bootcamp.infrastructure.helper.constant.SqlConstants;
import com.bootcamp.infrastructure.helper.mapper.BootcampMapper;
import com.bootcamp.infrastructure.output.r2dbc.repository.IBootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BootcampR2dbcPersistenceAdapter implements IBootcampPersistencePort {

    private final IBootcampRepository bootcampRepository;
    private final BootcampMapper bootcampMapper;
    private final DatabaseClient databaseClient;

    @Override
    public Mono<Bootcamp> save(Bootcamp bootcamp) {
        return bootcampRepository
                .save(bootcampMapper.toEntity(bootcamp))
                .map(bootcampMapper::toDomain);
    }

    @Override
    public Flux<Bootcamp> findAll(PageRequest pageRequest) {
        int offset = pageRequest.getPage() * pageRequest.getSize();
        String sqlQuery = this.getQuery(pageRequest);

        return databaseClient
                .sql(sqlQuery)
                .bind(SqlConstants.SIZE_STRING, pageRequest.getSize())
                .bind(SqlConstants.OFFSET_STRING, offset)
                .fetch()
                .all()
                .bufferUntilChanged(row -> row.get(SqlConstants.BOOTCAMP_ID))
                .map(this::buildBootcamp);
    }

    private Bootcamp buildBootcamp(List<Map<String, Object>> rows) {
        Bootcamp bootcamp = Bootcamp.builder()
                .id((Long) rows.get(0).get(SqlConstants.BOOTCAMP_ID))
                .name((String) rows.get(0).get(SqlConstants.BOOTCAMP_NAME))
                .description((String) rows.get(0).get(SqlConstants.BOOTCAMP_DESCRIPTION))
                .releaseDate((LocalDateTime) rows.get(0).get(SqlConstants.BOOTCAMP_RELEASE_DATE))
                .durationDays((int) rows.get(0).get(SqlConstants.BOOTCAMP_DURATION_DAYS))
                .build();

        List<Capability> capabilities = rows.stream()
                .filter(row -> row.get(SqlConstants.CAPABILITY_ID) != null)
                .collect(Collectors.groupingBy(row -> (Long) row.get(SqlConstants.CAPABILITY_ID)))
                .values().stream()
                .map(this::buildCapability)
                .toList();

        bootcamp.setCapabilityList(capabilities);
        return bootcamp;
    }

    private Capability buildCapability(List<Map<String, Object>> rows) {
        return Capability.builder()
                .id((Long) rows.get(0).get(SqlConstants.CAPABILITY_ID))
                .name((String) rows.get(0).get(SqlConstants.CAPABILITY_NAME))
                .description((String) rows.get(0).get(SqlConstants.CAPABILITY_DESCRIPTION))
                .technologyList(rows.stream()
                        .filter(technologyRow -> technologyRow.get(SqlConstants.TECHNOLOGY_ID) != null)
                        .map(this::buildTechnology)
                        .toList())
                .build();
    }

    private Technology buildTechnology(Map<String, Object> row) {
        return Technology.builder()
                .id((Long) row.get(SqlConstants.TECHNOLOGY_ID))
                .name((String) row.get(SqlConstants.TECHNOLOGY_NAME))
                .description((String) row.get(SqlConstants.TECHNOLOGY_DESCRIPTION))
                .build();
    }

    private String getQuery(PageRequest pageRequest) {
        String sortBy = pageRequest.getSortBy();
        PageRequest.SortOrder sortOrder = pageRequest.getSortOrder();

        if (sortBy == null) return SqlConstants.FIND_ALL_BOOTCAMPS;

        return switch (sortBy.toLowerCase()) {
            case "name" -> SqlConstants.FIND_ALL_BOOTCAMPS_ORDER_BY_NAME
                    .formatted(sortOrder, sortOrder);
            case "capability_count" -> SqlConstants.FIND_ALL_BOOTCAMPS_ORDER_BY_CAPABILITY_COUNT
                    .formatted(sortOrder, sortOrder);
            default -> SqlConstants.FIND_ALL_BOOTCAMPS;
        };
    }
}
