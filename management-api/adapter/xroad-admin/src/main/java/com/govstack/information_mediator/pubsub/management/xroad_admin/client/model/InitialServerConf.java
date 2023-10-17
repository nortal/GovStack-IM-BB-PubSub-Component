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
 * security server initial configuration
 */
@JsonPropertyOrder({
  InitialServerConf.JSON_PROPERTY_OWNER_MEMBER_CLASS,
  InitialServerConf.JSON_PROPERTY_OWNER_MEMBER_CODE,
  InitialServerConf.JSON_PROPERTY_SECURITY_SERVER_CODE,
  InitialServerConf.JSON_PROPERTY_SOFTWARE_TOKEN_PIN,
  InitialServerConf.JSON_PROPERTY_IGNORE_WARNINGS
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class InitialServerConf {
  public static final String JSON_PROPERTY_OWNER_MEMBER_CLASS = "owner_member_class";
  private String ownerMemberClass;

  public static final String JSON_PROPERTY_OWNER_MEMBER_CODE = "owner_member_code";
  private String ownerMemberCode;

  public static final String JSON_PROPERTY_SECURITY_SERVER_CODE = "security_server_code";
  private String securityServerCode;

  public static final String JSON_PROPERTY_SOFTWARE_TOKEN_PIN = "software_token_pin";
  private String softwareTokenPin;

  public static final String JSON_PROPERTY_IGNORE_WARNINGS = "ignore_warnings";
  private Boolean ignoreWarnings = false;

  public InitialServerConf() {
  }

  public InitialServerConf ownerMemberClass(String ownerMemberClass) {
    
    this.ownerMemberClass = ownerMemberClass;
    return this;
  }

   /**
   * member class
   * @return ownerMemberClass
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_OWNER_MEMBER_CLASS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getOwnerMemberClass() {
    return ownerMemberClass;
  }


  @JsonProperty(JSON_PROPERTY_OWNER_MEMBER_CLASS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOwnerMemberClass(String ownerMemberClass) {
    this.ownerMemberClass = ownerMemberClass;
  }


  public InitialServerConf ownerMemberCode(String ownerMemberCode) {
    
    this.ownerMemberCode = ownerMemberCode;
    return this;
  }

   /**
   * member code
   * @return ownerMemberCode
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_OWNER_MEMBER_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getOwnerMemberCode() {
    return ownerMemberCode;
  }


  @JsonProperty(JSON_PROPERTY_OWNER_MEMBER_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOwnerMemberCode(String ownerMemberCode) {
    this.ownerMemberCode = ownerMemberCode;
  }


  public InitialServerConf securityServerCode(String securityServerCode) {
    
    this.securityServerCode = securityServerCode;
    return this;
  }

   /**
   * security server code
   * @return securityServerCode
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SECURITY_SERVER_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getSecurityServerCode() {
    return securityServerCode;
  }


  @JsonProperty(JSON_PROPERTY_SECURITY_SERVER_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSecurityServerCode(String securityServerCode) {
    this.securityServerCode = securityServerCode;
  }


  public InitialServerConf softwareTokenPin(String softwareTokenPin) {
    
    this.softwareTokenPin = softwareTokenPin;
    return this;
  }

   /**
   * pin code for the initial software token
   * @return softwareTokenPin
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SOFTWARE_TOKEN_PIN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getSoftwareTokenPin() {
    return softwareTokenPin;
  }


  @JsonProperty(JSON_PROPERTY_SOFTWARE_TOKEN_PIN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSoftwareTokenPin(String softwareTokenPin) {
    this.softwareTokenPin = softwareTokenPin;
  }


  public InitialServerConf ignoreWarnings(Boolean ignoreWarnings) {
    
    this.ignoreWarnings = ignoreWarnings;
    return this;
  }

   /**
   * if true, any ignorable warnings are ignored. if false (or missing), any warnings cause request to fail
   * @return ignoreWarnings
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IGNORE_WARNINGS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getIgnoreWarnings() {
    return ignoreWarnings;
  }


  @JsonProperty(JSON_PROPERTY_IGNORE_WARNINGS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIgnoreWarnings(Boolean ignoreWarnings) {
    this.ignoreWarnings = ignoreWarnings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InitialServerConf initialServerConf = (InitialServerConf) o;
    return Objects.equals(this.ownerMemberClass, initialServerConf.ownerMemberClass) &&
        Objects.equals(this.ownerMemberCode, initialServerConf.ownerMemberCode) &&
        Objects.equals(this.securityServerCode, initialServerConf.securityServerCode) &&
        Objects.equals(this.softwareTokenPin, initialServerConf.softwareTokenPin) &&
        Objects.equals(this.ignoreWarnings, initialServerConf.ignoreWarnings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ownerMemberClass, ownerMemberCode, securityServerCode, softwareTokenPin, ignoreWarnings);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InitialServerConf {\n");
    sb.append("    ownerMemberClass: ").append(toIndentedString(ownerMemberClass)).append("\n");
    sb.append("    ownerMemberCode: ").append(toIndentedString(ownerMemberCode)).append("\n");
    sb.append("    securityServerCode: ").append(toIndentedString(securityServerCode)).append("\n");
    sb.append("    softwareTokenPin: ").append(toIndentedString(softwareTokenPin)).append("\n");
    sb.append("    ignoreWarnings: ").append(toIndentedString(ignoreWarnings)).append("\n");
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
