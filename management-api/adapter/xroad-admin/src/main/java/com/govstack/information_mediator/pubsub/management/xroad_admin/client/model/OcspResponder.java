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
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.DiagnosticStatusClass;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.OcspStatus;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * OCSP responder diagnostics
 */
@JsonPropertyOrder({
  OcspResponder.JSON_PROPERTY_URL,
  OcspResponder.JSON_PROPERTY_STATUS_CLASS,
  OcspResponder.JSON_PROPERTY_STATUS_CODE,
  OcspResponder.JSON_PROPERTY_PREV_UPDATE_AT,
  OcspResponder.JSON_PROPERTY_NEXT_UPDATE_AT
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class OcspResponder {
  public static final String JSON_PROPERTY_URL = "url";
  private String url;

  public static final String JSON_PROPERTY_STATUS_CLASS = "status_class";
  private DiagnosticStatusClass statusClass;

  public static final String JSON_PROPERTY_STATUS_CODE = "status_code";
  private OcspStatus statusCode;

  public static final String JSON_PROPERTY_PREV_UPDATE_AT = "prev_update_at";
  private OffsetDateTime prevUpdateAt;

  public static final String JSON_PROPERTY_NEXT_UPDATE_AT = "next_update_at";
  private OffsetDateTime nextUpdateAt;

  public OcspResponder() {
  }

  @JsonCreator
  public OcspResponder(
    @JsonProperty(JSON_PROPERTY_URL) String url, 
    @JsonProperty(JSON_PROPERTY_PREV_UPDATE_AT) OffsetDateTime prevUpdateAt, 
    @JsonProperty(JSON_PROPERTY_NEXT_UPDATE_AT) OffsetDateTime nextUpdateAt
  ) {
    this();
    this.url = url;
    this.prevUpdateAt = prevUpdateAt;
    this.nextUpdateAt = nextUpdateAt;
  }

   /**
   * url of the OCSP responder
   * @return url
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_URL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getUrl() {
    return url;
  }




  public OcspResponder statusClass(DiagnosticStatusClass statusClass) {
    
    this.statusClass = statusClass;
    return this;
  }

   /**
   * Get statusClass
   * @return statusClass
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_STATUS_CLASS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public DiagnosticStatusClass getStatusClass() {
    return statusClass;
  }


  @JsonProperty(JSON_PROPERTY_STATUS_CLASS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStatusClass(DiagnosticStatusClass statusClass) {
    this.statusClass = statusClass;
  }


  public OcspResponder statusCode(OcspStatus statusCode) {
    
    this.statusCode = statusCode;
    return this;
  }

   /**
   * Get statusCode
   * @return statusCode
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_STATUS_CODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OcspStatus getStatusCode() {
    return statusCode;
  }


  @JsonProperty(JSON_PROPERTY_STATUS_CODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStatusCode(OcspStatus statusCode) {
    this.statusCode = statusCode;
  }


   /**
   * last time updated
   * @return prevUpdateAt
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PREV_UPDATE_AT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OffsetDateTime getPrevUpdateAt() {
    return prevUpdateAt;
  }




   /**
   * next time updated
   * @return nextUpdateAt
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NEXT_UPDATE_AT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OffsetDateTime getNextUpdateAt() {
    return nextUpdateAt;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OcspResponder ocspResponder = (OcspResponder) o;
    return Objects.equals(this.url, ocspResponder.url) &&
        Objects.equals(this.statusClass, ocspResponder.statusClass) &&
        Objects.equals(this.statusCode, ocspResponder.statusCode) &&
        Objects.equals(this.prevUpdateAt, ocspResponder.prevUpdateAt) &&
        Objects.equals(this.nextUpdateAt, ocspResponder.nextUpdateAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, statusClass, statusCode, prevUpdateAt, nextUpdateAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OcspResponder {\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    statusClass: ").append(toIndentedString(statusClass)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    prevUpdateAt: ").append(toIndentedString(prevUpdateAt)).append("\n");
    sb.append("    nextUpdateAt: ").append(toIndentedString(nextUpdateAt)).append("\n");
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
