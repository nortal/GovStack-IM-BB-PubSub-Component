package com.govstack.information_mediator.pubsub.shared.jooq;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOnConditionStep;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <p> A class that provides caller with an implementation of a {@code org.jooq
 * UpdatableRecord} type.
 *
 * <p> It is up to the caller to decide whether to store the record and whether
 * to update its fields before storing.
 *
 * <p> This is the main entry point for creating or updating database entity
 * records in GovStack IM-BB PubSub Component.
 *
 * <p> RecordFactory depends on Lombok to handle constructor creation and adding
 * <code>throws</code> statements to method declarations.
 *
 * @author allan.bernard@nortal.com
 * @author madis.loitmaa@nortal.com
 * @author eduard.vigula@nortal.com
 * @see DSL
 * @see UpdatableRecord
 * @since 0.0.1
 */
@RequiredArgsConstructor
@SuppressWarnings("rawtypes, unchecked")
public class RecordFactory<T extends UpdatableRecord> {

    private static final Field<UUID> ID = DSL.field("id", UUID.class);
    private static final Field<Instant> DELETED_AT = DSL.field("deleted_at", Instant.class);

    private final DSLContext dsl;

    /**
     * <p> Returns an {@code UpdatableRecord} using the specific type from the input
     * provided. Honors soft-delete, records with deleted_at set, will not be returned,
     * regardless of other conditions.
     *
     * <p> If recordId parameter is provided the method will try to fetch an existing
     * record.
     *
     * <p> If recordId parameter is {@code null}, a new record is created and returned.
     *
     * @param recordClass The specific {@code UpdatableRecord} class type to be created
     * @param recordId    {@code UUID} of an existing record if an existing record should be loaded
     * @return An {@code UpdatableRecord} of the type provided by {@code recordClass} parameter
     * @throws ApiException if record id was provided but record not found
     */
    @SneakyThrows
    public T loadOrCreate(@NonNull Class<T> recordClass, UUID recordId) {
        var table = recordClass.getDeclaredConstructor().newInstance().getTable();
        return recordId == null
            ? (T) dsl.newRecord(table)
            : loadExistingRecordFrom(recordClass, recordId);
    }

    /**
     * <p> Returns {@code Optional<UpdatableRecord>} using the specific type from the input
     * provided and the condition provided.
     *
     * <p> Honors soft-delete, records with deleted_at set, will not be returned, regardless of other conditions.
     *
     * @param recordClass The specific {@code UpdatableRecord} class type to be created
     * @param condition   {@code DSL} condition for finding the specific record
     * @return Optional of {@code UpdatableRecord} of the type provided by {@code recordClass} parameter
     * @see DSL
     * @see Optional
     */
    @SneakyThrows
    public Optional<T> loadUsingCondition(@NonNull Class<T> recordClass, @NonNull Condition condition) {
        var table = recordClass.getDeclaredConstructor().newInstance().getTable();
        var updatableRecord = (T) dsl.fetchOne(table, condition.and(isNotDeleted(table)));
        return Optional.ofNullable(updatableRecord);
    }

    /**
     * <p> Returns {@code List<UpdatableRecord>} using the specific type from the input
     * provided and the condition provided.
     *
     * <p> Honors soft-delete, records with deleted_at set, will not be returned, regardless of other conditions.
     *
     * <p> Returns an empty list if no available records are found.
     *
     * @param recordClass The specific {@code UpdatableRecord} class type to be created
     * @param condition   {@code DSL} condition for finding the specific record
     * @return List of {@code UpdatableRecord} of the type provided by {@code recordClass} parameter
     * @see DSL
     * @see List
     */
    @SneakyThrows
    public List<T> loadListUsingCondition(@NonNull Class<T> recordClass, @NonNull Condition condition) {
        var table = recordClass.getDeclaredConstructor().newInstance().getTable();
        return dsl.select()
            .from(table)
            .where(condition.and(isNotDeleted(table)))
            .fetchInto(recordClass);
    }

    /**
     * <p> Returns {@code Optional<UpdatableRecord>} from table specified in join condition
     * using the specific type from the input and the condition provided.
     *
     * <p> Honors soft-delete, records with deleted_at set, will not be returned, regardless of other conditions.
     *
     * @param table         The {@code Table} to be loaded from
     * @param joinCondition {@code TableOnConditionStep<Record>} specific table condition to select from
     * @param condition     {@code DSL} condition for finding the specific record
     * @return Optional of {@code UpdatableRecord} of the type provided by {@code recordClass} parameter
     * @see Optional
     * @see TableOnConditionStep
     * @see DSL
     */
    @SneakyThrows
    public Optional<T> loadJoiningUsingCondition(TableImpl<T> table, TableOnConditionStep<Record> joinCondition, Condition condition) {
        var record = dsl.select(table.asterisk())
            .from(joinCondition)
            .where(condition.and(isNotDeleted(table)))
            .fetchOneInto(table.getRecordType());
        return Optional.ofNullable(record);
    }

    @SneakyThrows
    public List<T> loadListJoiningUsingCondition(TableImpl<T> table, TableOnConditionStep<Record> joinCondition, Condition condition) {
        return dsl.select(table.asterisk())
            .from(joinCondition)
            .where(condition.and(isNotDeleted(table)))
            .fetchInto(table.getRecordType());
    }

    public <V> Optional<V> retrieveField(@NonNull TableImpl<T> table, @NonNull TableField<T, V> field, @NonNull Condition condition) {
        var record = dsl.select(field)
            .from(table)
            .where(condition.and(isNotDeleted(table)))
            .fetchOne();
        return Optional.ofNullable(record)
            .map(r -> r.get(field));
    }

    private T loadExistingRecordFrom(Class<T> recordClass, UUID recordId) {
        return loadUsingCondition(recordClass, ID.eq(recordId)).orElseThrow(
            () -> new ApiException("Record [" + recordClass.getName() + "] with id " + recordId + " not found")
        );
    }

    public static Condition isNotDeleted(Table<?> table) {
        return table.field(DELETED_AT).isNull();
    }
}
