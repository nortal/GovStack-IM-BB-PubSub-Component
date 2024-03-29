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
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Key;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Key and TokenCertificateSigningRequest id
 */
@JsonPropertyOrder({
  KeyWithCertificateSigningRequestId.JSON_PROPERTY_KEY,
  KeyWithCertificateSigningRequestId.JSON_PROPERTY_CSR_ID
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class KeyWithCertificateSigningRequestId {
  public static final String JSON_PROPERTY_KEY = "key";
  private Key key;

  public static final String JSON_PROPERTY_CSR_ID = "csr_id";
  private String csrId;

  public KeyWithCertificateSigningRequestId() {
  }

  public KeyWithCertificateSigningRequestId key(Key key) {
    
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_KEY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Key getKey() {
    return key;
  }


  @JsonProperty(JSON_PROPERTY_KEY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setKey(Key key) {
    this.key = key;
  }


  public KeyWithCertificateSigningRequestId csrId(String csrId) {
    
    this.csrId = csrId;
    return this;
  }

   /**
   * CSR id
   * @return csrId
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CSR_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getCsrId() {
    return csrId;
  }


  @JsonProperty(JSON_PROPERTY_CSR_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCsrId(String csrId) {
    this.csrId = csrId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KeyWithCertificateSigningRequestId keyWithCertificateSigningRequestId = (KeyWithCertificateSigningRequestId) o;
    return Objects.equals(this.key, keyWithCertificateSigningRequestId.key) &&
        Objects.equals(this.csrId, keyWithCertificateSigningRequestId.csrId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, csrId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KeyWithCertificateSigningRequestId {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    csrId: ").append(toIndentedString(csrId)).append("\n");
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

