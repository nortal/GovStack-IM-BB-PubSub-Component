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
 * &lt;p&gt;The enum can have three different values&lt;/p&gt; &lt;ul&gt; &lt;li&gt;PRIMARY, which is the primary node in a high availability setup&lt;/li&gt; &lt;li&gt;SECONDARY, which is a secondary node in a high availability setup – a read-only server&lt;/li&gt; &lt;li&gt;STANDALONE,when there are no load balancer or high availability configured&lt;/li&gt; &lt;/ul&gt;
 */
public enum NodeType {
  
  PRIMARY("PRIMARY"),
  
  SECONDARY("SECONDARY"),
  
  STANDALONE("STANDALONE");

  private String value;

  NodeType(String value) {
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
  public static NodeType fromValue(String value) {
    for (NodeType b : NodeType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

