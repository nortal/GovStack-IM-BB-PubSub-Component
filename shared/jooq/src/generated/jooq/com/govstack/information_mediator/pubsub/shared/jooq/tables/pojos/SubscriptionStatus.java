/*
 * This file is generated by jOOQ.
 */
package com.govstack.information_mediator.pubsub.shared.jooq.tables.pojos;


import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;


/**
 * record of the status of a specific subscription
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SubscriptionStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final UUID subscriptionId;
    private final String status;
    private final Instant updatedAt;

    public SubscriptionStatus(SubscriptionStatus value) {
        this.id = value.id;
        this.subscriptionId = value.subscriptionId;
        this.status = value.status;
        this.updatedAt = value.updatedAt;
    }

    public SubscriptionStatus(
        UUID id,
        UUID subscriptionId,
        String status,
        Instant updatedAt
    ) {
        this.id = id;
        this.subscriptionId = subscriptionId;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    /**
     * Getter for <code>im_msg.subscription_status.id</code>. (technical field)
     * primary key of record
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Getter for <code>im_msg.subscription_status.subscription_id</code>.
     * (technical field) foreign key connected to the record
     */
    public UUID getSubscriptionId() {
        return this.subscriptionId;
    }

    /**
     * Getter for <code>im_msg.subscription_status.status</code>. enumerator of
     * the status to be assigned to the subscription
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Getter for <code>im_msg.subscription_status.updated_at</code>. moment of
     * assigning the status to a subscription
     */
    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SubscriptionStatus other = (SubscriptionStatus) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.subscriptionId == null) {
            if (other.subscriptionId != null)
                return false;
        }
        else if (!this.subscriptionId.equals(other.subscriptionId))
            return false;
        if (this.status == null) {
            if (other.status != null)
                return false;
        }
        else if (!this.status.equals(other.status))
            return false;
        if (this.updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        }
        else if (!this.updatedAt.equals(other.updatedAt))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.subscriptionId == null) ? 0 : this.subscriptionId.hashCode());
        result = prime * result + ((this.status == null) ? 0 : this.status.hashCode());
        result = prime * result + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SubscriptionStatus (");

        sb.append(id);
        sb.append(", ").append(subscriptionId);
        sb.append(", ").append(status);
        sb.append(", ").append(updatedAt);

        sb.append(")");
        return sb.toString();
    }
}
