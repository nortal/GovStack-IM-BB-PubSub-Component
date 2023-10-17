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
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.KeyUsageType;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.PossibleAction;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.TokenCertificate;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.TokenCertificateSigningRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Key for the certificate. Also includes the possible actions that can be done to this object, e.g DELETE (only for key related operations and does not consider user authorization).
 */
@JsonPropertyOrder({
  Key.JSON_PROPERTY_ID,
  Key.JSON_PROPERTY_NAME,
  Key.JSON_PROPERTY_LABEL,
  Key.JSON_PROPERTY_CERTIFICATES,
  Key.JSON_PROPERTY_CERTIFICATE_SIGNING_REQUESTS,
  Key.JSON_PROPERTY_USAGE,
  Key.JSON_PROPERTY_AVAILABLE,
  Key.JSON_PROPERTY_SAVED_TO_CONFIGURATION,
  Key.JSON_PROPERTY_POSSIBLE_ACTIONS
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class Key {
  public static final String JSON_PROPERTY_ID = "id";
  private String id;

  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_LABEL = "label";
  private String label;

  public static final String JSON_PROPERTY_CERTIFICATES = "certificates";
  private List<TokenCertificate> certificates = new ArrayList<>();

  public static final String JSON_PROPERTY_CERTIFICATE_SIGNING_REQUESTS = "certificate_signing_requests";
  private List<TokenCertificateSigningRequest> certificateSigningRequests = new ArrayList<>();

  public static final String JSON_PROPERTY_USAGE = "usage";
  private KeyUsageType usage;

  public static final String JSON_PROPERTY_AVAILABLE = "available";
  private Boolean available;

  public static final String JSON_PROPERTY_SAVED_TO_CONFIGURATION = "saved_to_configuration";
  private Boolean savedToConfiguration;

  public static final String JSON_PROPERTY_POSSIBLE_ACTIONS = "possible_actions";
  private List<PossibleAction> possibleActions;

  public Key() {
  }

  @JsonCreator
  public Key(
    @JsonProperty(JSON_PROPERTY_ID) String id
  ) {
    this();
    this.id = id;
  }

   /**
   * key id
   * @return id
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getId() {
    return id;
  }




  public Key name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * key name
   * @return name
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getName() {
    return name;
  }


  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setName(String name) {
    this.name = name;
  }


  public Key label(String label) {
    
    this.label = label;
    return this;
  }

   /**
   * key label
   * @return label
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_LABEL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getLabel() {
    return label;
  }


  @JsonProperty(JSON_PROPERTY_LABEL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLabel(String label) {
    this.label = label;
  }


  public Key certificates(List<TokenCertificate> certificates) {
    
    this.certificates = certificates;
    return this;
  }

  public Key addCertificatesItem(TokenCertificate certificatesItem) {
    if (this.certificates == null) {
      this.certificates = new ArrayList<>();
    }
    this.certificates.add(certificatesItem);
    return this;
  }

   /**
   * list of certificates for the key
   * @return certificates
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CERTIFICATES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<TokenCertificate> getCertificates() {
    return certificates;
  }


  @JsonProperty(JSON_PROPERTY_CERTIFICATES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCertificates(List<TokenCertificate> certificates) {
    this.certificates = certificates;
  }


  public Key certificateSigningRequests(List<TokenCertificateSigningRequest> certificateSigningRequests) {
    
    this.certificateSigningRequests = certificateSigningRequests;
    return this;
  }

  public Key addCertificateSigningRequestsItem(TokenCertificateSigningRequest certificateSigningRequestsItem) {
    if (this.certificateSigningRequests == null) {
      this.certificateSigningRequests = new ArrayList<>();
    }
    this.certificateSigningRequests.add(certificateSigningRequestsItem);
    return this;
  }

   /**
   * list of CSRs for the key
   * @return certificateSigningRequests
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CERTIFICATE_SIGNING_REQUESTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<TokenCertificateSigningRequest> getCertificateSigningRequests() {
    return certificateSigningRequests;
  }


  @JsonProperty(JSON_PROPERTY_CERTIFICATE_SIGNING_REQUESTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCertificateSigningRequests(List<TokenCertificateSigningRequest> certificateSigningRequests) {
    this.certificateSigningRequests = certificateSigningRequests;
  }


  public Key usage(KeyUsageType usage) {
    
    this.usage = usage;
    return this;
  }

   /**
   * Get usage
   * @return usage
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_USAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public KeyUsageType getUsage() {
    return usage;
  }


  @JsonProperty(JSON_PROPERTY_USAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUsage(KeyUsageType usage) {
    this.usage = usage;
  }


  public Key available(Boolean available) {
    
    this.available = available;
    return this;
  }

   /**
   * if the key is available
   * @return available
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_AVAILABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getAvailable() {
    return available;
  }


  @JsonProperty(JSON_PROPERTY_AVAILABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAvailable(Boolean available) {
    this.available = available;
  }


  public Key savedToConfiguration(Boolean savedToConfiguration) {
    
    this.savedToConfiguration = savedToConfiguration;
    return this;
  }

   /**
   * if the key is saved to configuration
   * @return savedToConfiguration
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SAVED_TO_CONFIGURATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getSavedToConfiguration() {
    return savedToConfiguration;
  }


  @JsonProperty(JSON_PROPERTY_SAVED_TO_CONFIGURATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSavedToConfiguration(Boolean savedToConfiguration) {
    this.savedToConfiguration = savedToConfiguration;
  }


  public Key possibleActions(List<PossibleAction> possibleActions) {
    
    this.possibleActions = possibleActions;
    return this;
  }

  public Key addPossibleActionsItem(PossibleAction possibleActionsItem) {
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
    Key key = (Key) o;
    return Objects.equals(this.id, key.id) &&
        Objects.equals(this.name, key.name) &&
        Objects.equals(this.label, key.label) &&
        Objects.equals(this.certificates, key.certificates) &&
        Objects.equals(this.certificateSigningRequests, key.certificateSigningRequests) &&
        Objects.equals(this.usage, key.usage) &&
        Objects.equals(this.available, key.available) &&
        Objects.equals(this.savedToConfiguration, key.savedToConfiguration) &&
        Objects.equals(this.possibleActions, key.possibleActions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, label, certificates, certificateSigningRequests, usage, available, savedToConfiguration, possibleActions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Key {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    certificates: ").append(toIndentedString(certificates)).append("\n");
    sb.append("    certificateSigningRequests: ").append(toIndentedString(certificateSigningRequests)).append("\n");
    sb.append("    usage: ").append(toIndentedString(usage)).append("\n");
    sb.append("    available: ").append(toIndentedString(available)).append("\n");
    sb.append("    savedToConfiguration: ").append(toIndentedString(savedToConfiguration)).append("\n");
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

