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
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.MessageLogArchiveEncryptionMember;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * message log encryption statuses
 */
@JsonPropertyOrder({
  MessageLogEncryptionStatus.JSON_PROPERTY_MESSAGE_LOG_ARCHIVE_ENCRYPTION_STATUS,
  MessageLogEncryptionStatus.JSON_PROPERTY_MESSAGE_LOG_DATABASE_ENCRYPTION_STATUS,
  MessageLogEncryptionStatus.JSON_PROPERTY_MESSAGE_LOG_GROUPING_RULE,
  MessageLogEncryptionStatus.JSON_PROPERTY_MEMBERS
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class MessageLogEncryptionStatus {
  public static final String JSON_PROPERTY_MESSAGE_LOG_ARCHIVE_ENCRYPTION_STATUS = "message_log_archive_encryption_status";
  private Boolean messageLogArchiveEncryptionStatus;

  public static final String JSON_PROPERTY_MESSAGE_LOG_DATABASE_ENCRYPTION_STATUS = "message_log_database_encryption_status";
  private Boolean messageLogDatabaseEncryptionStatus;

  public static final String JSON_PROPERTY_MESSAGE_LOG_GROUPING_RULE = "message_log_grouping_rule";
  private String messageLogGroupingRule;

  public static final String JSON_PROPERTY_MEMBERS = "members";
  private List<MessageLogArchiveEncryptionMember> members;

  public MessageLogEncryptionStatus() {
  }

  @JsonCreator
  public MessageLogEncryptionStatus(
    @JsonProperty(JSON_PROPERTY_MESSAGE_LOG_ARCHIVE_ENCRYPTION_STATUS) Boolean messageLogArchiveEncryptionStatus, 
    @JsonProperty(JSON_PROPERTY_MESSAGE_LOG_DATABASE_ENCRYPTION_STATUS) Boolean messageLogDatabaseEncryptionStatus, 
    @JsonProperty(JSON_PROPERTY_MESSAGE_LOG_GROUPING_RULE) String messageLogGroupingRule
  ) {
    this();
    this.messageLogArchiveEncryptionStatus = messageLogArchiveEncryptionStatus;
    this.messageLogDatabaseEncryptionStatus = messageLogDatabaseEncryptionStatus;
    this.messageLogGroupingRule = messageLogGroupingRule;
  }

   /**
   * Get messageLogArchiveEncryptionStatus
   * @return messageLogArchiveEncryptionStatus
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MESSAGE_LOG_ARCHIVE_ENCRYPTION_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Boolean getMessageLogArchiveEncryptionStatus() {
    return messageLogArchiveEncryptionStatus;
  }




   /**
   * Get messageLogDatabaseEncryptionStatus
   * @return messageLogDatabaseEncryptionStatus
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MESSAGE_LOG_DATABASE_ENCRYPTION_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Boolean getMessageLogDatabaseEncryptionStatus() {
    return messageLogDatabaseEncryptionStatus;
  }




   /**
   * Get messageLogGroupingRule
   * @return messageLogGroupingRule
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MESSAGE_LOG_GROUPING_RULE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getMessageLogGroupingRule() {
    return messageLogGroupingRule;
  }




  public MessageLogEncryptionStatus members(List<MessageLogArchiveEncryptionMember> members) {
    
    this.members = members;
    return this;
  }

  public MessageLogEncryptionStatus addMembersItem(MessageLogArchiveEncryptionMember membersItem) {
    if (this.members == null) {
      this.members = new ArrayList<>();
    }
    this.members.add(membersItem);
    return this;
  }

   /**
   * Get members
   * @return members
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MEMBERS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<MessageLogArchiveEncryptionMember> getMembers() {
    return members;
  }


  @JsonProperty(JSON_PROPERTY_MEMBERS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMembers(List<MessageLogArchiveEncryptionMember> members) {
    this.members = members;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageLogEncryptionStatus messageLogEncryptionStatus = (MessageLogEncryptionStatus) o;
    return Objects.equals(this.messageLogArchiveEncryptionStatus, messageLogEncryptionStatus.messageLogArchiveEncryptionStatus) &&
        Objects.equals(this.messageLogDatabaseEncryptionStatus, messageLogEncryptionStatus.messageLogDatabaseEncryptionStatus) &&
        Objects.equals(this.messageLogGroupingRule, messageLogEncryptionStatus.messageLogGroupingRule) &&
        Objects.equals(this.members, messageLogEncryptionStatus.members);
  }

  @Override
  public int hashCode() {
    return Objects.hash(messageLogArchiveEncryptionStatus, messageLogDatabaseEncryptionStatus, messageLogGroupingRule, members);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageLogEncryptionStatus {\n");
    sb.append("    messageLogArchiveEncryptionStatus: ").append(toIndentedString(messageLogArchiveEncryptionStatus)).append("\n");
    sb.append("    messageLogDatabaseEncryptionStatus: ").append(toIndentedString(messageLogDatabaseEncryptionStatus)).append("\n");
    sb.append("    messageLogGroupingRule: ").append(toIndentedString(messageLogGroupingRule)).append("\n");
    sb.append("    members: ").append(toIndentedString(members)).append("\n");
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

