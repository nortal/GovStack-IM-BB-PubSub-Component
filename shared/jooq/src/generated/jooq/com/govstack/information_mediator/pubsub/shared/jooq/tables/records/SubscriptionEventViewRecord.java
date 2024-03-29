/*
 * This file is generated by jOOQ.
 */
package com.govstack.information_mediator.pubsub.shared.jooq.tables.records;


import com.govstack.information_mediator.pubsub.shared.jooq.tables.SubscriptionEventView;

import java.time.Instant;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SubscriptionEventViewRecord extends TableRecordImpl<SubscriptionEventViewRecord> implements Record6<UUID, String, Instant, UUID, Integer, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>im_msg.subscription_event_view.event_id</code>.
     */
    public SubscriptionEventViewRecord setEventId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.subscription_event_view.event_id</code>.
     */
    public UUID getEventId() {
        return (UUID) get(0);
    }

    /**
     * Setter for
     * <code>im_msg.subscription_event_view.event_type_identifier</code>.
     */
    public SubscriptionEventViewRecord setEventTypeIdentifier(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for
     * <code>im_msg.subscription_event_view.event_type_identifier</code>.
     */
    public String getEventTypeIdentifier() {
        return (String) get(1);
    }

    /**
     * Setter for <code>im_msg.subscription_event_view.event_created_at</code>.
     */
    public SubscriptionEventViewRecord setEventCreatedAt(Instant value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.subscription_event_view.event_created_at</code>.
     */
    public Instant getEventCreatedAt() {
        return (Instant) get(2);
    }

    /**
     * Setter for <code>im_msg.subscription_event_view.subscription_id</code>.
     */
    public SubscriptionEventViewRecord setSubscriptionId(UUID value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.subscription_event_view.subscription_id</code>.
     */
    public UUID getSubscriptionId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>im_msg.subscription_event_view.delivery_attempts</code>.
     */
    public SubscriptionEventViewRecord setDeliveryAttempts(Integer value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.subscription_event_view.delivery_attempts</code>.
     */
    public Integer getDeliveryAttempts() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>im_msg.subscription_event_view.delivery_status</code>.
     */
    public SubscriptionEventViewRecord setDeliveryStatus(String value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.subscription_event_view.delivery_status</code>.
     */
    public String getDeliveryStatus() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, String, Instant, UUID, Integer, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<UUID, String, Instant, UUID, Integer, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return SubscriptionEventView.SUBSCRIPTION_EVENT_VIEW.EVENT_ID;
    }

    @Override
    public Field<String> field2() {
        return SubscriptionEventView.SUBSCRIPTION_EVENT_VIEW.EVENT_TYPE_IDENTIFIER;
    }

    @Override
    public Field<Instant> field3() {
        return SubscriptionEventView.SUBSCRIPTION_EVENT_VIEW.EVENT_CREATED_AT;
    }

    @Override
    public Field<UUID> field4() {
        return SubscriptionEventView.SUBSCRIPTION_EVENT_VIEW.SUBSCRIPTION_ID;
    }

    @Override
    public Field<Integer> field5() {
        return SubscriptionEventView.SUBSCRIPTION_EVENT_VIEW.DELIVERY_ATTEMPTS;
    }

    @Override
    public Field<String> field6() {
        return SubscriptionEventView.SUBSCRIPTION_EVENT_VIEW.DELIVERY_STATUS;
    }

    @Override
    public UUID component1() {
        return getEventId();
    }

    @Override
    public String component2() {
        return getEventTypeIdentifier();
    }

    @Override
    public Instant component3() {
        return getEventCreatedAt();
    }

    @Override
    public UUID component4() {
        return getSubscriptionId();
    }

    @Override
    public Integer component5() {
        return getDeliveryAttempts();
    }

    @Override
    public String component6() {
        return getDeliveryStatus();
    }

    @Override
    public UUID value1() {
        return getEventId();
    }

    @Override
    public String value2() {
        return getEventTypeIdentifier();
    }

    @Override
    public Instant value3() {
        return getEventCreatedAt();
    }

    @Override
    public UUID value4() {
        return getSubscriptionId();
    }

    @Override
    public Integer value5() {
        return getDeliveryAttempts();
    }

    @Override
    public String value6() {
        return getDeliveryStatus();
    }

    @Override
    public SubscriptionEventViewRecord value1(UUID value) {
        setEventId(value);
        return this;
    }

    @Override
    public SubscriptionEventViewRecord value2(String value) {
        setEventTypeIdentifier(value);
        return this;
    }

    @Override
    public SubscriptionEventViewRecord value3(Instant value) {
        setEventCreatedAt(value);
        return this;
    }

    @Override
    public SubscriptionEventViewRecord value4(UUID value) {
        setSubscriptionId(value);
        return this;
    }

    @Override
    public SubscriptionEventViewRecord value5(Integer value) {
        setDeliveryAttempts(value);
        return this;
    }

    @Override
    public SubscriptionEventViewRecord value6(String value) {
        setDeliveryStatus(value);
        return this;
    }

    @Override
    public SubscriptionEventViewRecord values(UUID value1, String value2, Instant value3, UUID value4, Integer value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SubscriptionEventViewRecord
     */
    public SubscriptionEventViewRecord() {
        super(SubscriptionEventView.SUBSCRIPTION_EVENT_VIEW);
    }

    /**
     * Create a detached, initialised SubscriptionEventViewRecord
     */
    public SubscriptionEventViewRecord(UUID eventId, String eventTypeIdentifier, Instant eventCreatedAt, UUID subscriptionId, Integer deliveryAttempts, String deliveryStatus) {
        super(SubscriptionEventView.SUBSCRIPTION_EVENT_VIEW);

        setEventId(eventId);
        setEventTypeIdentifier(eventTypeIdentifier);
        setEventCreatedAt(eventCreatedAt);
        setSubscriptionId(subscriptionId);
        setDeliveryAttempts(deliveryAttempts);
        setDeliveryStatus(deliveryStatus);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised SubscriptionEventViewRecord
     */
    public SubscriptionEventViewRecord(com.govstack.information_mediator.pubsub.shared.jooq.tables.pojos.SubscriptionEventView value) {
        super(SubscriptionEventView.SUBSCRIPTION_EVENT_VIEW);

        if (value != null) {
            setEventId(value.getEventId());
            setEventTypeIdentifier(value.getEventTypeIdentifier());
            setEventCreatedAt(value.getEventCreatedAt());
            setSubscriptionId(value.getSubscriptionId());
            setDeliveryAttempts(value.getDeliveryAttempts());
            setDeliveryStatus(value.getDeliveryStatus());
            resetChangedOnNotNull();
        }
    }
}
