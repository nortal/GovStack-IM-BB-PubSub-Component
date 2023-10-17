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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * object that contains a code identifier and possibly collection of associated metadata or validation errors. Used to relay error and warning information.
 */
@JsonPropertyOrder({
  CodeWithDetails.JSON_PROPERTY_CODE,
  CodeWithDetails.JSON_PROPERTY_METADATA,
  CodeWithDetails.JSON_PROPERTY_VALIDATION_ERRORS
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class CodeWithDetails {
  public static final String JSON_PROPERTY_CODE = "code";
  private String code;

  public static final String JSON_PROPERTY_METADATA = "metadata";
  private List<String> metadata;

  public static final String JSON_PROPERTY_VALIDATION_ERRORS = "validation_errors";
  private Map<String, List<String>> validationErrors = new HashMap<>();

  public CodeWithDetails() {
  }

  public CodeWithDetails code(String code) {
    
    this.code = code;
    return this;
  }

   /**
   * identifier of the item (for example errorcode)
   * @return code
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getCode() {
    return code;
  }


  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCode(String code) {
    this.code = code;
  }


  public CodeWithDetails metadata(List<String> metadata) {
    
    this.metadata = metadata;
    return this;
  }

  public CodeWithDetails addMetadataItem(String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

   /**
   * array containing metadata associated with the item. For example names of services were attempted to add, but failed
   * @return metadata
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getMetadata() {
    return metadata;
  }


  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMetadata(List<String> metadata) {
    this.metadata = metadata;
  }


  public CodeWithDetails validationErrors(Map<String, List<String>> validationErrors) {
    
    this.validationErrors = validationErrors;
    return this;
  }

  public CodeWithDetails putValidationErrorsItem(String key, List<String> validationErrorsItem) {
    if (this.validationErrors == null) {
      this.validationErrors = new HashMap<>();
    }
    this.validationErrors.put(key, validationErrorsItem);
    return this;
  }

   /**
   * A dictionary object that contains validation errors bound to their respected fields. The key represents the field where the validation error has happened and the value is a list of validation errors
   * @return validationErrors
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VALIDATION_ERRORS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, List<String>> getValidationErrors() {
    return validationErrors;
  }


  @JsonProperty(JSON_PROPERTY_VALIDATION_ERRORS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setValidationErrors(Map<String, List<String>> validationErrors) {
    this.validationErrors = validationErrors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CodeWithDetails codeWithDetails = (CodeWithDetails) o;
    return Objects.equals(this.code, codeWithDetails.code) &&
        Objects.equals(this.metadata, codeWithDetails.metadata) &&
        Objects.equals(this.validationErrors, codeWithDetails.validationErrors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, metadata, validationErrors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CodeWithDetails {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    validationErrors: ").append(toIndentedString(validationErrors)).append("\n");
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
