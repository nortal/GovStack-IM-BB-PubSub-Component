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
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.CertificateDetails;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.CertificateOcspStatus;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.CertificateStatus;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.PossibleAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Certificate that is stored in a Token (auth or sign cert). Also includes the possible actions that can be done to this object, e.g DELETE (only for cert related operations and does not consider user authorization).
 */
@JsonPropertyOrder({
  TokenCertificate.JSON_PROPERTY_OCSP_STATUS,
  TokenCertificate.JSON_PROPERTY_OWNER_ID,
  TokenCertificate.JSON_PROPERTY_ACTIVE,
  TokenCertificate.JSON_PROPERTY_SAVED_TO_CONFIGURATION,
  TokenCertificate.JSON_PROPERTY_CERTIFICATE_DETAILS,
  TokenCertificate.JSON_PROPERTY_STATUS,
  TokenCertificate.JSON_PROPERTY_POSSIBLE_ACTIONS
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class TokenCertificate {
  public static final String JSON_PROPERTY_OCSP_STATUS = "ocsp_status";
  private CertificateOcspStatus ocspStatus;

  public static final String JSON_PROPERTY_OWNER_ID = "owner_id";
  private String ownerId;

  public static final String JSON_PROPERTY_ACTIVE = "active";
  private Boolean active;

  public static final String JSON_PROPERTY_SAVED_TO_CONFIGURATION = "saved_to_configuration";
  private Boolean savedToConfiguration;

  public static final String JSON_PROPERTY_CERTIFICATE_DETAILS = "certificate_details";
  private CertificateDetails certificateDetails;

  public static final String JSON_PROPERTY_STATUS = "status";
  private CertificateStatus status;

  public static final String JSON_PROPERTY_POSSIBLE_ACTIONS = "possible_actions";
  private List<PossibleAction> possibleActions;

  public TokenCertificate() {
  }

  @JsonCreator
  public TokenCertificate(
    @JsonProperty(JSON_PROPERTY_OWNER_ID) String ownerId
  ) {
    this();
    this.ownerId = ownerId;
  }

  public TokenCertificate ocspStatus(CertificateOcspStatus ocspStatus) {
    
    this.ocspStatus = ocspStatus;
    return this;
  }

   /**
   * Get ocspStatus
   * @return ocspStatus
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_OCSP_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public CertificateOcspStatus getOcspStatus() {
    return ocspStatus;
  }


  @JsonProperty(JSON_PROPERTY_OCSP_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setOcspStatus(CertificateOcspStatus ocspStatus) {
    this.ocspStatus = ocspStatus;
  }


   /**
   * client id of the owner member, &lt;instance_id&gt;:&lt;member_class&gt;:&lt;member_code&gt;
   * @return ownerId
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_OWNER_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getOwnerId() {
    return ownerId;
  }




  public TokenCertificate active(Boolean active) {
    
    this.active = active;
    return this;
  }

   /**
   * if the certificate is active
   * @return active
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ACTIVE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Boolean getActive() {
    return active;
  }


  @JsonProperty(JSON_PROPERTY_ACTIVE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setActive(Boolean active) {
    this.active = active;
  }


  public TokenCertificate savedToConfiguration(Boolean savedToConfiguration) {
    
    this.savedToConfiguration = savedToConfiguration;
    return this;
  }

   /**
   * if the certificate is saved to configuration
   * @return savedToConfiguration
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SAVED_TO_CONFIGURATION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Boolean getSavedToConfiguration() {
    return savedToConfiguration;
  }


  @JsonProperty(JSON_PROPERTY_SAVED_TO_CONFIGURATION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSavedToConfiguration(Boolean savedToConfiguration) {
    this.savedToConfiguration = savedToConfiguration;
  }


  public TokenCertificate certificateDetails(CertificateDetails certificateDetails) {
    
    this.certificateDetails = certificateDetails;
    return this;
  }

   /**
   * Get certificateDetails
   * @return certificateDetails
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CERTIFICATE_DETAILS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public CertificateDetails getCertificateDetails() {
    return certificateDetails;
  }


  @JsonProperty(JSON_PROPERTY_CERTIFICATE_DETAILS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCertificateDetails(CertificateDetails certificateDetails) {
    this.certificateDetails = certificateDetails;
  }


  public TokenCertificate status(CertificateStatus status) {
    
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public CertificateStatus getStatus() {
    return status;
  }


  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStatus(CertificateStatus status) {
    this.status = status;
  }


  public TokenCertificate possibleActions(List<PossibleAction> possibleActions) {
    
    this.possibleActions = possibleActions;
    return this;
  }

  public TokenCertificate addPossibleActionsItem(PossibleAction possibleActionsItem) {
    if (this.possibleActions == null) {
      this.possibleActions = new ArrayList<>();
    }
    this.possibleActions.add(possibleActionsItem);
    return this;
  }

   /**
   * array containing the possible actions that can be done for this item
   * @return possibleActions
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_POSSIBLE_ACTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<PossibleAction> getPossibleActions() {
    return possibleActions;
  }


  @JsonProperty(JSON_PROPERTY_POSSIBLE_ACTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPossibleActions(List<PossibleAction> possibleActions) {
    this.possibleActions = possibleActions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenCertificate tokenCertificate = (TokenCertificate) o;
    return Objects.equals(this.ocspStatus, tokenCertificate.ocspStatus) &&
        Objects.equals(this.ownerId, tokenCertificate.ownerId) &&
        Objects.equals(this.active, tokenCertificate.active) &&
        Objects.equals(this.savedToConfiguration, tokenCertificate.savedToConfiguration) &&
        Objects.equals(this.certificateDetails, tokenCertificate.certificateDetails) &&
        Objects.equals(this.status, tokenCertificate.status) &&
        Objects.equals(this.possibleActions, tokenCertificate.possibleActions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ocspStatus, ownerId, active, savedToConfiguration, certificateDetails, status, possibleActions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenCertificate {\n");
    sb.append("    ocspStatus: ").append(toIndentedString(ocspStatus)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    savedToConfiguration: ").append(toIndentedString(savedToConfiguration)).append("\n");
    sb.append("    certificateDetails: ").append(toIndentedString(certificateDetails)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    possibleActions: ").append(toIndentedString(possibleActions)).append("\n");
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
