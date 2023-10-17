package com.govstack.information_mediator.pubsub.management.domain.usecase.manager;

import com.govstack.information_mediator.pubsub.domain.entity.Manager;

import java.util.List;

public interface ExportManagersUseCase {

    Response execute();

    record Response(List<Manager> managers) { }
}
