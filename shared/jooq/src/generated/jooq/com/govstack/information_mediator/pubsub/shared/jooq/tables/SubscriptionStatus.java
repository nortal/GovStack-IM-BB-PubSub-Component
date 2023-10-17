/*
 * This file is generated by jOOQ.
 */
package com.govstack.information_mediator.pubsub.shared.jooq.tables;


import com.govstack.information_mediator.pubsub.shared.jooq.ImMsg;
import com.govstack.information_mediator.pubsub.shared.jooq.Keys;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.SubscriptionStatusRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function4;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row4;
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
 * record of the status of a specific subscription
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SubscriptionStatus extends TableImpl<SubscriptionStatusRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>im_msg.subscription_status</code>
     */
    public static final SubscriptionStatus SUBSCRIPTION_STATUS = new SubscriptionStatus();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SubscriptionStatusRecord> getRecordType() {
        return SubscriptionStatusRecord.class;
    }

    /**
     * The column <code>im_msg.subscription_status.id</code>. (technical field)
     * primary key of record
     */
    public final TableField<SubscriptionStatusRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "(technical field) primary key of record");

    /**
     * The column <code>im_msg.subscription_status.subscription_id</code>.
     * (technical field) foreign key connected to the record
     */
    public final TableField<SubscriptionStatusRecord, UUID> SUBSCRIPTION_ID = createField(DSL.name("subscription_id"), SQLDataType.UUID.nullable(false), this, "(technical field) foreign key connected to the record");

    /**
     * The column <code>im_msg.subscription_status.status</code>. enumerator of
     * the status to be assigned to the subscription
     */
    public final TableField<SubscriptionStatusRecord, String> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR.nullable(false), this, "enumerator of the status to be assigned to the subscription");

    /**
     * The column <code>im_msg.subscription_status.updated_at</code>. moment of
     * assigning the status to a subscription
     */
    public final TableField<SubscriptionStatusRecord, Instant> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.INSTANT.nullable(false), this, "moment of assigning the status to a subscription");

    private SubscriptionStatus(Name alias, Table<SubscriptionStatusRecord> aliased) {
        this(alias, aliased, null);
    }

    private SubscriptionStatus(Name alias, Table<SubscriptionStatusRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("record of the status of a specific subscription"), TableOptions.table());
    }

    /**
     * Create an aliased <code>im_msg.subscription_status</code> table reference
     */
    public SubscriptionStatus(String alias) {
        this(DSL.name(alias), SUBSCRIPTION_STATUS);
    }

    /**
     * Create an aliased <code>im_msg.subscription_status</code> table reference
     */
    public SubscriptionStatus(Name alias) {
        this(alias, SUBSCRIPTION_STATUS);
    }

    /**
     * Create a <code>im_msg.subscription_status</code> table reference
     */
    public SubscriptionStatus() {
        this(DSL.name("subscription_status"), null);
    }

    public <O extends Record> SubscriptionStatus(Table<O> child, ForeignKey<O, SubscriptionStatusRecord> key) {
        super(child, key, SUBSCRIPTION_STATUS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : ImMsg.IM_MSG;
    }

    @Override
    public UniqueKey<SubscriptionStatusRecord> getPrimaryKey() {
        return Keys.PK_SUBSCRIPTION_STATUS;
    }

    @Override
    public List<ForeignKey<SubscriptionStatusRecord, ?>> getReferences() {
        return Arrays.asList(Keys.SUBSCRIPTION_STATUS__FK_SUBSCRIPTION_STATUS_SUBSCRIPTION_ID);
    }

    private transient Subscriptions _subscriptions;

    /**
     * Get the implicit join path to the <code>im_msg.subscriptions</code>
     * table.
     */
    public Subscriptions subscriptions() {
        if (_subscriptions == null)
            _subscriptions = new Subscriptions(this, Keys.SUBSCRIPTION_STATUS__FK_SUBSCRIPTION_STATUS_SUBSCRIPTION_ID);

        return _subscriptions;
    }

    @Override
    public SubscriptionStatus as(String alias) {
        return new SubscriptionStatus(DSL.name(alias), this);
    }

    @Override
    public SubscriptionStatus as(Name alias) {
        return new SubscriptionStatus(alias, this);
    }

    @Override
    public SubscriptionStatus as(Table<?> alias) {
        return new SubscriptionStatus(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public SubscriptionStatus rename(String name) {
        return new SubscriptionStatus(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SubscriptionStatus rename(Name name) {
        return new SubscriptionStatus(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public SubscriptionStatus rename(Table<?> name) {
        return new SubscriptionStatus(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, UUID, String, Instant> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function4<? super UUID, ? super UUID, ? super String, ? super Instant, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function4<? super UUID, ? super UUID, ? super String, ? super Instant, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}