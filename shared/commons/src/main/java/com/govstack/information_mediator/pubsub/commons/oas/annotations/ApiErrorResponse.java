package com.govstack.information_mediator.pubsub.commons.oas.annotations;

import java.time.LocalDateTime;

public record ApiErrorResponse(LocalDateTime timestamp, String message) { }
