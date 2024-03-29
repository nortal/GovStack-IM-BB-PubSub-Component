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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * TokenPinUpdate
 */
@JsonPropertyOrder({
  TokenPinUpdate.JSON_PROPERTY_OLD_PIN,
  TokenPinUpdate.JSON_PROPERTY_NEW_PIN
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class TokenPinUpdate {
  public static final String JSON_PROPERTY_OLD_PIN = "old_pin";
  private String oldPin;

  public static final String JSON_PROPERTY_NEW_PIN = "new_pin";
  private String newPin;

  public TokenPinUpdate() {
  }

  public TokenPinUpdate oldPin(String oldPin) {
    
    this.oldPin = oldPin;
    return this;
  }

   /**
   * the old pin code of the token
   * @return oldPin
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_OLD_PIN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getOldPin() {
    return oldPin;
  }


  @JsonProperty(JSON_PROPERTY_OLD_PIN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setOldPin(String oldPin) {
    this.oldPin = oldPin;
  }


  public TokenPinUpdate newPin(String newPin) {
    
    this.newPin = newPin;
    return this;
  }

   /**
   * the new pin code of the token
   * @return newPin
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NEW_PIN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getNewPin() {
    return newPin;
  }


  @JsonProperty(JSON_PROPERTY_NEW_PIN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNewPin(String newPin) {
    this.newPin = newPin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenPinUpdate tokenPinUpdate = (TokenPinUpdate) o;
    return Objects.equals(this.oldPin, tokenPinUpdate.oldPin) &&
        Objects.equals(this.newPin, tokenPinUpdate.newPin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(oldPin, newPin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenPinUpdate {\n");
    sb.append("    oldPin: ").append(toIndentedString(oldPin)).append("\n");
    sb.append("    newPin: ").append(toIndentedString(newPin)).append("\n");
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

