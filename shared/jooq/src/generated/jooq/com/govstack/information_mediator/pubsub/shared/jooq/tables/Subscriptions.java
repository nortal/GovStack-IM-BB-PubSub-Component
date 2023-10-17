/*
 * This file is generated by jOOQ.
 */
package com.govstack.information_mediator.pubsub.shared.jooq.tables;


import com.govstack.information_mediator.pubsub.shared.jooq.ImMsg;
import com.govstack.information_mediator.pubsub.shared.jooq.Keys;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.SubscriptionsRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.jooq.Check;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function8;
import org.jooq.JSONB;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * record of subscriptions to a specific room
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Subscriptions extends TableImpl<SubscriptionsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>im_msg.subscriptions</code>
     */
    public static final Subscriptions SUBSCRIPTIONS = new Subscriptions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SubscriptionsRecord> getRecordType() {
        return SubscriptionsRecord.class;
    }

    /**
     * The column <code>im_msg.subscriptions.id</code>. (technical field)
     * primary key of record
     */
    public final TableField<SubscriptionsRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "(technical field) primary key of record");

    /**
     * The column <code>im_msg.subscriptions.room_id</code>. (technical field)
     * foreign key connected to the record
     */
    public final TableField<SubscriptionsRecord, UUID> ROOM_ID = createField(DSL.name("room_id"), SQLDataType.UUID.nullable(false), this, "(technical field) foreign key connected to the record");

    /**
     * The column <code>im_msg.subscriptions.event_type_id</code>. (technical
     * field) foreign key connected to the record
     */
    public final TableField<SubscriptionsRecord, UUID> EVENT_TYPE_ID = createField(DSL.name("event_type_id"), SQLDataType.UUID.nullable(false), this, "(technical field) foreign key connected to the record");

    /**
     * The column <code>im_msg.subscriptions.identifier_type</code>. enumerator
     * specifying the source of identity
     */
    public final TableField<SubscriptionsRecord, String> IDENTIFIER_TYPE = createField(DSL.name("identifier_type"), SQLDataType.VARCHAR.nullable(false), this, "enumerator specifying the source of identity");

    /**
     * The column <code>im_msg.subscriptions.identifier</code>. identification
     * token/code of the specified type
     */
    public final TableField<SubscriptionsRecord, String> IDENTIFIER = createField(DSL.name("identifier"), SQLDataType.VARCHAR.nullable(false), this, "identification token/code of the specified type");

    /**
     * The column <code>im_msg.subscriptions.parameters</code>. subscription
     * parameters
     */
    public final TableField<SubscriptionsRecord, JSONB> PARAMETERS = createField(DSL.name("parameters"), SQLDataType.JSONB.nullable(false), this, "subscription parameters");

    /**
     * The column <code>im_msg.subscriptions.journal</code>. (technical field)
     * auditable journal of record changes
     */
    public final TableField<SubscriptionsRecord, JSONB> JOURNAL = createField(DSL.name("journal"), SQLDataType.JSONB.nullable(false), this, "(technical field) auditable journal of record changes");

    /**
     * The column <code>im_msg.subscriptions.deleted_at</code>. (technical
     * field) deletion moment of the record
     */
    public final TableField<SubscriptionsRecord, Instant> DELETED_AT = createField(DSL.name("deleted_at"), SQLDataType.INSTANT, this, "(technical field) deletion moment of the record");

    private Subscriptions(Name alias, Table<SubscriptionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Subscriptions(Name alias, Table<SubscriptionsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("record of subscriptions to a specific room"), TableOptions.table());
    }

    /**
     * Create an aliased <code>im_msg.subscriptions</code> table reference
     */
    public Subscriptions(String alias) {
        this(DSL.name(alias), SUBSCRIPTIONS);
    }

    /**
     * Create an aliased <code>im_msg.subscriptions</code> table reference
     */
    public Subscriptions(Name alias) {
        this(alias, SUBSCRIPTIONS);
    }

    /**
     * Create a <code>im_msg.subscriptions</code> table reference
     */
    public Subscriptions() {
        this(DSL.name("subscriptions"), null);
    }

    public <O extends Record> Subscriptions(Table<O> child, ForeignKey<O, SubscriptionsRecord> key) {
        super(child, key, SUBSCRIPTIONS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : ImMsg.IM_MSG;
    }

    @Override
    public UniqueKey<SubscriptionsRecord> getPrimaryKey() {
        return Keys.PK_SUBSCRIPTIONS;
    }

    @Override
    public List<ForeignKey<SubscriptionsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.SUBSCRIPTIONS__FK_SUBSCRIPTIONS_ROOM_ID, Keys.SUBSCRIPTIONS__FK_SUBSCRIPTIONS_EVENT_TYPE_ID);
    }

    private transient Rooms _rooms;
    private transient EventTypes _eventTypes;

    /**
     * Get the implicit join path to the <code>im_msg.rooms</code> table.
     */
    public Rooms rooms() {
        if (_rooms == null)
            _rooms = new Rooms(this, Keys.SUBSCRIPTIONS__FK_SUBSCRIPTIONS_ROOM_ID);

        return _rooms;
    }

    /**
     * Get the implicit join path to the <code>im_msg.event_types</code> table.
     */
    public EventTypes eventTypes() {
        if (_eventTypes == null)
            _eventTypes = new EventTypes(this, Keys.SUBSCRIPTIONS__FK_SUBSCRIPTIONS_EVENT_TYPE_ID);

        return _eventTypes;
    }

    @Override
    public List<Check<SubscriptionsRecord>> getChecks() {
        return Arrays.asList(
            Internal.createCheck(this, DSL.name("ch_subscriptions_method"), "(((((((parameters ->> 'method'::text))::character varying)::text = 'PULL'::text) AND (((parameters ->> 'pushUrl'::text))::character varying IS NULL)) OR (((((parameters ->> 'method'::text))::character varying)::text = 'PUSH'::text) AND (((parameters ->> 'pushUrl'::text))::character varying IS NOT NULL))))", true)
        );
    }

    @Override
    public Subscriptions as(String alias) {
        return new Subscriptions(DSL.name(alias), this);
    }

    @Override
    public Subscriptions as(Name alias) {
        return new Subscriptions(alias, this);
    }

    @Override
    public Subscriptions as(Table<?> alias) {
        return new Subscriptions(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Subscriptions rename(String name) {
        return new Subscriptions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Subscriptions rename(Name name) {
        return new Subscriptions(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Subscriptions rename(Table<?> name) {
        return new Subscriptions(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<UUID, UUID, UUID, String, String, JSONB, JSONB, Instant> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function8<? super UUID, ? super UUID, ? super UUID, ? super String, ? super String, ? super JSONB, ? super JSONB, ? super Instant, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function8<? super UUID, ? super UUID, ? super UUID, ? super String, ? super String, ? super JSONB, ? super JSONB, ? super Instant, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}