/*
 * This file is generated by jOOQ.
 */
package com.govstack.information_mediator.pubsub.shared.jooq.tables.records;


import com.govstack.information_mediator.pubsub.shared.jooq.tables.Events;

import java.time.Instant;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * record of all published events
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EventsRecord extends UpdatableRecordImpl<EventsRecord> implements Record7<UUID, UUID, UUID, UUID, UUID, String, Instant> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>im_msg.events.id</code>. (technical field) primary key
     * of record
     */
    public EventsRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.events.id</code>. (technical field) primary key
     * of record
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>im_msg.events.room_id</code>. (technical field) foreign
     * key connected to the record
     */
    public EventsRecord setRoomId(UUID value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.events.room_id</code>. (technical field) foreign
     * key connected to the record
     */
    public UUID getRoomId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>im_msg.events.event_type_version_id</code>. (technical
     * field) foreign key connected to the record
     */
    public EventsRecord setEventTypeVersionId(UUID value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.events.event_type_version_id</code>. (technical
     * field) foreign key connected to the record
     */
    public UUID getEventTypeVersionId() {
        return (UUID) get(2);
    }

    /**
     * Setter for <code>im_msg.events.event_type_id</code>. (technical field)
     * foreign key connected to the record
     */
    public EventsRecord setEventTypeId(UUID value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.events.event_type_id</code>. (technical field)
     * foreign key connected to the record
     */
    public UUID getEventTypeId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>im_msg.events.publisher_id</code>. (technical field)
     * foreign key connected to the record
     */
    public EventsRecord setPublisherId(UUID value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.events.publisher_id</code>. (technical field)
     * foreign key connected to the record
     */
    public UUID getPublisherId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>im_msg.events.correlation_id</code>. Correlation ID of
     * the event linking event messages in different queues
     */
    public EventsRecord setCorrelationId(String value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.events.correlation_id</code>. Correlation ID of
     * the event linking event messages in different queues
     */
    public String getCorrelationId() {
        return (String) get(5);
    }

    /**
     * Setter for <code>im_msg.events.created_at</code>. Filled with a timestamp
     * when ActiveMQ Artemis acknowledges publishing
     */
    public EventsRecord setCreatedAt(Instant value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>im_msg.events.created_at</code>. Filled with a timestamp
     * when ActiveMQ Artemis acknowledges publishing
     */
    public Instant getCreatedAt() {
        return (Instant) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<UUID, UUID, UUID, UUID, UUID, String, Instant> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<UUID, UUID, UUID, UUID, UUID, String, Instant> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Events.EVENTS.ID;
    }

    @Override
    public Field<UUID> field2() {
        return Events.EVENTS.ROOM_ID;
    }

    @Override
    public Field<UUID> field3() {
        return Events.EVENTS.EVENT_TYPE_VERSION_ID;
    }

    @Override
    public Field<UUID> field4() {
        return Events.EVENTS.EVENT_TYPE_ID;
    }

    @Override
    public Field<UUID> field5() {
        return Events.EVENTS.PUBLISHER_ID;
    }

    @Override
    public Field<String> field6() {
        return Events.EVENTS.CORRELATION_ID;
    }

    @Override
    public Field<Instant> field7() {
        return Events.EVENTS.CREATED_AT;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public UUID component2() {
        return getRoomId();
    }

    @Override
    public UUID component3() {
        return getEventTypeVersionId();
    }

    @Override
    public UUID component4() {
        return getEventTypeId();
    }

    @Override
    public UUID component5() {
        return getPublisherId();
    }

    @Override
    public String component6() {
        return getCorrelationId();
    }

    @Override
    public Instant component7() {
        return getCreatedAt();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public UUID value2() {
        return getRoomId();
    }

    @Override
    public UUID value3() {
        return getEventTypeVersionId();
    }

    @Override
    public UUID value4() {
        return getEventTypeId();
    }

    @Override
    public UUID value5() {
        return getPublisherId();
    }

    @Override
    public String value6() {
        return getCorrelationId();
    }

    @Override
    public Instant value7() {
        return getCreatedAt();
    }

    @Override
    public EventsRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public EventsRecord value2(UUID value) {
        setRoomId(value);
        return this;
    }

    @Override
    public EventsRecord value3(UUID value) {
        setEventTypeVersionId(value);
        return this;
    }

    @Override
    public EventsRecord value4(UUID value) {
        setEventTypeId(value);
        return this;
    }

    @Override
    public EventsRecord value5(UUID value) {
        setPublisherId(value);
        return this;
    }

    @Override
    public EventsRecord value6(String value) {
        setCorrelationId(value);
        return this;
    }

    @Override
    public EventsRecord value7(Instant value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public EventsRecord values(UUID value1, UUID value2, UUID value3, UUID value4, UUID value5, String value6, Instant value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EventsRecord
     */
    public EventsRecord() {
        super(Events.EVENTS);
    }

    /**
     * Create a detached, initialised EventsRecord
     */
    public EventsRecord(UUID id, UUID roomId, UUID eventTypeVersionId, UUID eventTypeId, UUID publisherId, String correlationId, Instant createdAt) {
        super(Events.EVENTS);

        setId(id);
        setRoomId(roomId);
        setEventTypeVersionId(eventTypeVersionId);
        setEventTypeId(eventTypeId);
        setPublisherId(publisherId);
        setCorrelationId(correlationId);
        setCreatedAt(createdAt);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised EventsRecord
     */
    public EventsRecord(com.govstack.information_mediator.pubsub.shared.jooq.tables.pojos.Events value) {
        super(Events.EVENTS);

        if (value != null) {
            setId(value.getId());
            setRoomId(value.getRoomId());
            setEventTypeVersionId(value.getEventTypeVersionId());
            setEventTypeId(value.getEventTypeId());
            setPublisherId(value.getPublisherId());
            setCorrelationId(value.getCorrelationId());
            setCreatedAt(value.getCreatedAt());
            resetChangedOnNotNull();
        }
    }
}
