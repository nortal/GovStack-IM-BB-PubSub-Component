package com.govstack.information_mediator.pubsub.commons.exception;

public class ApiException extends RuntimeException {

  public ApiException(String message) {
    super(message);
  }

  ApiException() {
    this(null);
  }
}
