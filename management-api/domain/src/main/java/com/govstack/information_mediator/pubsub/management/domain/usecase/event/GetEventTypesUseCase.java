package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeView;

public interface GetEventTypesUseCase {

    Response execute(Request request);

    record Request(ManagerIdentifier manager, RoomIdentifier room, PageRequest pageRequest) {

    }

    record Response(Page<EventTypeView> eventTypes) {

    }
}
