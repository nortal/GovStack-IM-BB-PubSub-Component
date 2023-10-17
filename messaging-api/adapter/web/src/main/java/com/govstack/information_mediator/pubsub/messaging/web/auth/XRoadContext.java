package com.govstack.information_mediator.pubsub.messaging.web.auth;

import jakarta.enterprise.context.RequestScoped;
import lombok.Data;

@Data
@RequestScoped
class XRoadContext {

    private String client;

}
