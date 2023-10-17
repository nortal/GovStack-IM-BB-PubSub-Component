/*
 * X-Road Security Server Admin API
 * X-Road Security Server Admin API. Note that the error metadata responses described in some endpoints are subjects to change and may be updated in upcoming versions.
 *
 * The version of the OpenAPI document: 2.1.0
 * Contact: info@niis.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.govstack.information_mediator.pubsub.management.xroad_admin.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * token type
 */
public enum TokenStatus {
  
  OK("OK"),
  
  USER_PIN_LOCKED("USER_PIN_LOCKED"),
  
  USER_PIN_INCORRECT("USER_PIN_INCORRECT"),
  
  USER_PIN_INVALID("USER_PIN_INVALID"),
  
  USER_PIN_EXPIRED("USER_PIN_EXPIRED"),
  
  USER_PIN_COUNT_LOW("USER_PIN_COUNT_LOW"),
  
  USER_PIN_FINAL_TRY("USER_PIN_FINAL_TRY"),
  
  NOT_INITIALIZED("NOT_INITIALIZED");

  private String value;

  TokenStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static TokenStatus fromValue(String value) {
    for (TokenStatus b : TokenStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

