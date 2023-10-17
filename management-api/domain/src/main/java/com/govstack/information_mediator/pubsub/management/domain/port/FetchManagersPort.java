package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FetchManagersPort {

    Optional<Manager> fetchManager(UUID id);

    Optional<Manager> fetchManager(String identifier);

    Optional<ManagerID> fetchManagerID(ManagerIdentifier managerIdentifier);

    List<Manager> fetchAllManagers();

    Page<Manager> fetchManagers(PageRequest pageRequest);
}
