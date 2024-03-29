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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.NodeType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Response object containing the node type of the Security Server
 */
@JsonPropertyOrder({
  NodeTypeResponse.JSON_PROPERTY_NODE_TYPE
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class NodeTypeResponse {
  public static final String JSON_PROPERTY_NODE_TYPE = "node_type";
  private NodeType nodeType;

  public NodeTypeResponse() {
  }

  public NodeTypeResponse nodeType(NodeType nodeType) {
    
    this.nodeType = nodeType;
    return this;
  }

   /**
   * Get nodeType
   * @return nodeType
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NODE_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public NodeType getNodeType() {
    return nodeType;
  }


  @JsonProperty(JSON_PROPERTY_NODE_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNodeType(NodeType nodeType) {
    this.nodeType = nodeType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NodeTypeResponse nodeTypeResponse = (NodeTypeResponse) o;
    return Objects.equals(this.nodeType, nodeTypeResponse.nodeType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodeType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NodeTypeResponse {\n");
    sb.append("    nodeType: ").append(toIndentedString(nodeType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

