/*
 * This file is generated by jOOQ.
 */
package com.govstack.information_mediator.pubsub.shared.jooq.tables;


import com.govstack.information_mediator.pubsub.shared.jooq.ImMsg;
import com.govstack.information_mediator.pubsub.shared.jooq.Keys;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.PublisherConstraintsRecord;

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
 * record of event types a specific publisher is allowed to create
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PublisherConstraints extends TableImpl<PublisherConstraintsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>im_msg.publisher_constraints</code>
     */
    public static final PublisherConstraints PUBLISHER_CONSTRAINTS = new PublisherConstraints();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PublisherConstraintsRecord> getRecordType() {
        return PublisherConstraintsRecord.class;
    }

    /**
     * The column <code>im_msg.publisher_constraints.id</code>. (technical
     * field) primary key of record
     */
    public final TableField<PublisherConstraintsRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "(technical field) primary key of record");

    /**
     * The column <code>im_msg.publisher_constraints.publisher_id</code>.
     * (technical field) foreign key connected to the record
     */
    public final TableField<PublisherConstraintsRecord, UUID> PUBLISHER_ID = createField(DSL.name("publisher_id"), SQLDataType.UUID.nullable(false), this, "(technical field) foreign key connected to the record");

    /**
     * The column <code>im_msg.publisher_constraints.event_type_id</code>.
     * (technical field) foreign key connected to the record
     */
    public final TableField<PublisherConstraintsRecord, UUID> EVENT_TYPE_ID = createField(DSL.name("event_type_id"), SQLDataType.UUID.nullable(false), this, "(technical field) foreign key connected to the record");

    /**
     * The column <code>im_msg.publisher_constraints.created_at</code>. creation
     * time of the publisher constraint
     */
    public final TableField<PublisherConstraintsRecord, Instant> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.INSTANT.nullable(false), this, "creation time of the publisher constraint");

    /**
     * The column <code>im_msg.publisher_constraints.deleted_at</code>.
     * (technical field) deletion moment of the record
     */
    public final TableField<PublisherConstraintsRecord, Instant> DELETED_AT = createField(DSL.name("deleted_at"), SQLDataType.INSTANT, this, "(technical field) deletion moment of the record");

    private PublisherConstraints(Name alias, Table<PublisherConstraintsRecord> aliased) {
        this(alias, aliased, null);
    }

    private PublisherConstraints(Name alias, Table<PublisherConstraintsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("record of event types a specific publisher is allowed to create"), TableOptions.table());
    }

    /**
     * Create an aliased <code>im_msg.publisher_constraints</code> table
     * reference
     */
    public PublisherConstraints(String alias) {
        this(DSL.name(alias), PUBLISHER_CONSTRAINTS);
    }

    /**
     * Create an aliased <code>im_msg.publisher_constraints</code> table
     * reference
     */
    public PublisherConstraints(Name alias) {
        this(alias, PUBLISHER_CONSTRAINTS);
    }

    /**
     * Create a <code>im_msg.publisher_constraints</code> table reference
     */
    public PublisherConstraints() {
        this(DSL.name("publisher_constraints"), null);
    }

    public <O extends Record> PublisherConstraints(Table<O> child, ForeignKey<O, PublisherConstraintsRecord> key) {
        super(child, key, PUBLISHER_CONSTRAINTS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : ImMsg.IM_MSG;
    }

    @Override
    public UniqueKey<PublisherConstraintsRecord> getPrimaryKey() {
        return Keys.PK_PUBLISHER_CONSTRAINTS;
    }

    @Override
    public List<UniqueKey<PublisherConstraintsRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.UQ_PUBLISHER_CONSTRAINTS);
    }

    @Override
    public List<ForeignKey<PublisherConstraintsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PUBLISHER_CONSTRAINTS__FK_PUBLISHER_CONSTRAINTS_PUBLISHERS_ID, Keys.PUBLISHER_CONSTRAINTS__FK_PUBLISHER_CONSTRAINTS_EVENT_TYPES_ID);
    }

    private transient Publishers _publishers;
    private transient EventTypes _eventTypes;

    /**
     * Get the implicit join path to the <code>im_msg.publishers</code> table.
     */
    public Publishers publishers() {
        if (_publishers == null)
            _publishers = new Publishers(this, Keys.PUBLISHER_CONSTRAINTS__FK_PUBLISHER_CONSTRAINTS_PUBLISHERS_ID);

        return _publishers;
    }

    /**
     * Get the implicit join path to the <code>im_msg.event_types</code> table.
     */
    public EventTypes eventTypes() {
        if (_eventTypes == null)
            _eventTypes = new EventTypes(this, Keys.PUBLISHER_CONSTRAINTS__FK_PUBLISHER_CONSTRAINTS_EVENT_TYPES_ID);

        return _eventTypes;
    }

    @Override
    public PublisherConstraints as(String alias) {
        return new PublisherConstraints(DSL.name(alias), this);
    }

    @Override
    public PublisherConstraints as(Name alias) {
        return new PublisherConstraints(alias, this);
    }

    @Override
    public PublisherConstraints as(Table<?> alias) {
        return new PublisherConstraints(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public PublisherConstraints rename(String name) {
        return new PublisherConstraints(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PublisherConstraints rename(Name name) {
        return new PublisherConstraints(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public PublisherConstraints rename(Table<?> name) {
        return new PublisherConstraints(name.getQualifiedName(), null);
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
