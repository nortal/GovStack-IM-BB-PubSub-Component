package com.govstack.information_mediator.pubsub.management.domain.usecase.member;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Member;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import lombok.Builder;
import lombok.Value;

public interface ListMembersUseCase {

    Response execute(Request request);

    @Value
    @Builder
    class Request {
        PageRequest pageRequest;
    }

    record Response(Page<Member> members) { }
}
