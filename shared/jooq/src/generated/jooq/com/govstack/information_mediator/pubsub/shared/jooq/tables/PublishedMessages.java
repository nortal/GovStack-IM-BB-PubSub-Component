/*
 * This file is generated by jOOQ.
 */
package com.govstack.information_mediator.pubsub.shared.jooq.tables;


import com.govstack.information_mediator.pubsub.shared.jooq.ImMsg;
import com.govstack.information_mediator.pubsub.shared.jooq.Keys;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.PublishedMessagesRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function5;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * record a message published to a subscriber
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PublishedMessages extends TableImpl<PublishedMessagesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>im_msg.published_messages</code>
     */
    public static final PublishedMessages PUBLISHED_MESSAGES = new PublishedMessages();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PublishedMessagesRecord> getRecordType() {
        return PublishedMessagesRecord.class;
    }

    /**
     * The column <code>im_msg.published_messages.id</code>. (technical field)
     * primary key of record
     */
    public final TableField<PublishedMessagesRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "(technical field) primary key of record");

    /**
     * The column <code>im_msg.published_messages.event_id</code>. (technical
     * field) foreign key connected to the record
     */
    public final TableField<PublishedMessagesRecord, UUID> EVENT_ID = createField(DSL.name("event_id"), SQLDataType.UUID.nullable(false), this, "(technical field) foreign key connected to the record");

    /**
     * The column <code>im_msg.published_messages.subscription_id</code>.
     * (technical field) foreign key connected to the record
     */
    public final TableField<PublishedMessagesRecord, UUID> SUBSCRIPTION_ID = createField(DSL.name("subscription_id"), SQLDataType.UUID.nullable(false), this, "(technical field) foreign key connected to the record");

    /**
     * The column <code>im_msg.published_messages.published_at</code>. moment
     * when a message is published to a subscriber based on an event
     */
    public final TableField<PublishedMessagesRecord, Instant> PUBLISHED_AT = createField(DSL.name("published_at"), SQLDataType.INSTANT, this, "moment when a message is published to a subscriber based on an event");

    /**
     * The column <code>im_msg.published_messages.delivered_at</code>. moment
     * when the subscriber pulls the message from their queue or the pushed
     * message is acknowledged by the subscriber
     */
    public final TableField<PublishedMessagesRecord, Instant> DELIVERED_AT = createField(DSL.name("delivered_at"), SQLDataType.INSTANT, this, "moment when the subscriber pulls the message from their queue or the pushed message is acknowledged by the subscriber");

    private PublishedMessages(Name alias, Table<PublishedMessagesRecord> aliased) {
        this(alias, aliased, null);
    }

    private PublishedMessages(Name alias, Table<PublishedMessagesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("record a message published to a subscriber"), TableOptions.table());
    }

    /**
     * Create an aliased <code>im_msg.published_messages</code> table reference
     */
    public PublishedMessages(String alias) {
        this(DSL.name(alias), PUBLISHED_MESSAGES);
    }

    /**
     * Create an aliased <code>im_msg.published_messages</code> table reference
     */
    public PublishedMessages(Name alias) {
        this(alias, PUBLISHED_MESSAGES);
    }

    /**
     * Create a <code>im_msg.published_messages</code> table reference
     */
    public PublishedMessages() {
        this(DSL.name("published_messages"), null);
    }

    public <O extends Record> PublishedMessages(Table<O> child, ForeignKey<O, PublishedMessagesRecord> key) {
        super(child, key, PUBLISHED_MESSAGES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : ImMsg.IM_MSG;
    }

    @Override
    public UniqueKey<PublishedMessagesRecord> getPrimaryKey() {
        return Keys.PK_PUBLISHED_MESSAGES;
    }

    @Override
    public List<ForeignKey<PublishedMessagesRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PUBLISHED_MESSAGES__FK_PUBLISHED_MESSAGES_EVENT_ID, Keys.PUBLISHED_MESSAGES__FK_PUBLISHED_MESSAGES_SUBSCRIPTION_ID);
    }

    private transient Events _events;
    private transient Subscriptions _subscriptions;

    /**
     * Get the implicit join path to the <code>im_msg.events</code> table.
     */
    public Events events() {
        if (_events == null)
            _events = new Events(this, Keys.PUBLISHED_MESSAGES__FK_PUBLISHED_MESSAGES_EVENT_ID);

        return _events;
    }

    /**
     * Get the implicit join path to the <code>im_msg.subscriptions</code>
     * table.
     */
    public Subscriptions subscriptions() {
        if (_subscriptions == null)
            _subscriptions = new Subscriptions(this, Keys.PUBLISHED_MESSAGES__FK_PUBLISHED_MESSAGES_SUBSCRIPTION_ID);

        return _subscriptions;
    }

    @Override
    public PublishedMessages as(String alias) {
        return new PublishedMessages(DSL.name(alias), this);
    }

    @Override
    public PublishedMessages as(Name alias) {
        return new PublishedMessages(alias, this);
    }

    @Override
    public PublishedMessages as(Table<?> alias) {
        return new PublishedMessages(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public PublishedMessages rename(String name) {
        return new PublishedMessages(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PublishedMessages rename(Name name) {
        return new PublishedMessages(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public PublishedMessages rename(Table<?> name) {
        return new PublishedMessages(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, UUID, UUID, Instant, Instant> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function5<? super UUID, ? super UUID, ? super UUID, ? super Instant, ? super Instant, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function5<? super UUID, ? super UUID, ? super UUID, ? super Instant, ? super Instant, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}