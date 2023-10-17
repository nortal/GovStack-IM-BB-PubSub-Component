package com.govstack.information_mediator.pubsub.management.domain.usecase.manager;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;

public interface RetrievePagedManagersUseCase {

    Response execute(Request request);

    record Request(PageRequest pageRequest) {

    }

    record Response(Page<Manager> managers) {

    }
}
