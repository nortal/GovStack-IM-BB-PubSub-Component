package com.govstack.information_mediator.pubsub.management.domain.usecase.publisher;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.MemberIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.view.PublisherView;

public interface GetPublishersUseCase {

    Response execute(Request request);

    record Request(MemberIdentifier member, RoomIdentifier room, PageRequest pageRequest) {

    }

    record Response(Page<PublisherView> publishers) {

    }
}
