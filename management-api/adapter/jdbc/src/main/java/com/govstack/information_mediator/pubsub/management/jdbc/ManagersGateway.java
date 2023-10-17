package com.govstack.information_mediator.pubsub.management.jdbc;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerAlreadyExistsException;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.TerminateManagersPort;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.ManagersRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.tools.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory.isNotDeleted;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.Managers.MANAGERS;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManagersGateway implements FetchManagersPort, CreateManagersPort, TerminateManagersPort {

    private final Clock clock;
    private final DSLContext dsl;
    private final RecordFactory<ManagersRecord> managersRecordFactory;
    private final List<OnManagerCascadeDeletionListener> onManagerCascadeDeletionListeners;


    @Override
    public Optional<Manager> fetchManager(UUID id) {
        var condition = MANAGERS.ID.eq(id);
        return managersRecordFactory.loadUsingCondition(ManagersRecord.class, condition).map(this::toDomainEntity);
    }

    @Override
    public Optional<Manager> fetchManager(String identifier) {
        var condition = MANAGERS.IDENTIFIER.eq(identifier);
        return managersRecordFactory.loadUsingCondition(ManagersRecord.class, condition).map(this::toDomainEntity);
    }

    @Override
    public Optional<ManagerID> fetchManagerID(ManagerIdentifier managerIdentifier) {
        var condition = MANAGERS.IDENTIFIER.eq(managerIdentifier.getIdentifier());
        return managersRecordFactory.retrieveField(MANAGERS, MANAGERS.ID, condition).map(ManagerID::new);
    }

    @Override
    public List<Manager> fetchAllManagers() {
        return managersRecordFactory.loadListUsingCondition(ManagersRecord.class, MANAGERS.DELETED_AT.isNull())
            .stream()
            .map(this::toDomainEntity)
            .toList();
    }

    @Override
    public Page<Manager> fetchManagers(PageRequest pageRequest) {
        // Calculate total number of pages
        int totalNumberOfElements = dsl.fetchCount(MANAGERS, isNotDeleted(MANAGERS));

        var managers = dsl.select(MANAGERS.asterisk())
            .from(MANAGERS)
            .where(isNotDeleted(MANAGERS))
            .orderBy(getOrderByTerms(pageRequest))
            .limit(pageRequest.getMaxItemsPerPage())
            .offset(pageRequest.getMaxItemsPerPage() * pageRequest.getPageOffset())
            .fetchInto(ManagersRecord.class)
            .stream()
            .map(this::toDomainEntity)
            .toList();

        return Page.<Manager>builder()
            .content(managers)
            .totalNumberOfElements(totalNumberOfElements)
            .maxItemsPerPage(pageRequest.getMaxItemsPerPage())
            .currentPageNumber(pageRequest.getPageOffset())
            .currentPageNumberOfElements(managers.size())
            .build();
    }

    private SortField<?> getOrderByTerms(PageRequest pageRequest) {
        if (StringUtils.equals(pageRequest.getSortBy(), "identifier")) {
            return pageRequest.isDescendingOrder() ? MANAGERS.IDENTIFIER.desc() : MANAGERS.IDENTIFIER.asc();
        }
        if (StringUtils.equals(pageRequest.getSortBy(), "identifierType")) {
            return pageRequest.isDescendingOrder() ? MANAGERS.IDENTIFIER_TYPE.desc() : MANAGERS.IDENTIFIER_TYPE.asc();
        }
        return pageRequest.isDescendingOrder() ? MANAGERS.ID.desc() : MANAGERS.ID.asc();
    }

    private Manager toDomainEntity(ManagersRecord record) {
        return Manager.builder()
            .id(record.getId())
            .identifier(record.getIdentifier())
            .identifierType(EnumUtils.getEnum(IdentifierType.class, record.getIdentifierType()))
            .build();
    }

    @Override
    public UUID createManager(Manager manager) {
        try {
            return persist(manager);
        } catch (DuplicateKeyException e) {
            throw new ManagerAlreadyExistsException(e);
        }
    }

    private UUID persist(Manager manager) {
        var record = managersRecordFactory.loadOrCreate(ManagersRecord.class, null);
        record.setIdentifier(manager.getIdentifier());
        record.setIdentifierType(manager.getIdentifierType().name());
        record.setCreatedAt(clock.instant());
        record.store();
        return record.getId();
    }

    @Override
    @Transactional
    public void terminate(Manager manager) {
        var condition = MANAGERS.ID.eq(manager.getId());
        managersRecordFactory.loadUsingCondition(ManagersRecord.class, condition)
            .ifPresent(record -> {
                record.setDeletedAt(clock.instant());
                record.store();

                onManagerCascadeDeletionListeners.forEach(listener -> listener.onManagerDeleted(manager.getId()));
            });
    }
}
