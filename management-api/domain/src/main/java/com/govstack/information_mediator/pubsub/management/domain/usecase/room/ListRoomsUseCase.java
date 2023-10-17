package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.view.RoomView;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

public interface ListRoomsUseCase {

    Response execute(Request request);

    @Value
    @RequiredArgsConstructor(staticName = "of")
    class Request {
        String memberIdentifier;
        PageRequest pageRequest;
    }

    record Response (Page<RoomView> rooms) {}
}
