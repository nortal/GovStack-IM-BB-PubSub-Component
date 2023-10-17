package com.govstack.information_mediator.pubsub

import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator
import com.govstack.information_mediator.pubsub.config.TestData
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType
import com.govstack.information_mediator.pubsub.domain.entity.Subscription
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import io.qameta.allure.Step

import java.time.Instant

class Database {

    String url
    String username
    String password

    @Lazy
    Sql db = {
        Sql.newInstance(url, username, password)
    }()

    String addManager(String identifier, IdentifierType identifierType = IdentifierType.XROAD, boolean updateOnConflict = false) {
        String id = UUIDGenerator.randomUUID()
        String query = "INSERT INTO managers(id, identifier_type, identifier, created_at, deleted_at)\n" +
                "VALUES ('$id', '${identifierType.name()}', '$identifier', now(), NULL)\n"
        if (updateOnConflict) {
            query += "ON CONFLICT(identifier, deleted_at) DO UPDATE SET deleted_at = NULL\n"
        }
        executeQuery(query, db.&execute)
        return id
    }

    void addManagers(int count) {
        for (i in 1..count) {
            addManager(TestData.getInternalManagerIdentifier(), IdentifierType.INTERNAL)

        }
    }

    List<GroovyRowResult> getActiveManagers(String identifier, IdentifierType identifierType) {
        String query = "SELECT *\n" +
                "FROM managers \n" +
                "WHERE identifier = '$identifier' \n" +
                "AND identifier_type= '${identifierType.toString()}' \n" +
                "AND deleted_at is NULL \n"
        return getRows(query)
    }

    List<GroovyRowResult> getManagers(String identifier) {
        String query = "SELECT *\n" +
                "FROM managers \n" +
                "WHERE identifier = '$identifier' \n"
        return getRows(query)
    }

    List<GroovyRowResult> getAllActiveManagers() {
        String query = "SELECT *\n" +
                "FROM managers \n" +
                "WHERE deleted_at is NULL \n"
        return getRows(query)
    }

    void terminateManager(String managerId) {
        softDelete("managers", managerId)
    }

    void terminateActiveManagerByIdentifier(String managerIdentifier) {
        String query = "UPDATE managers\n" +
                "SET deleted_at = now()\n" +
                "WHERE identifier = '$managerIdentifier'\n" +
                "AND deleted_at is NULL\n"
        executeQuery(query, db.&execute)
    }

    String addRoom(String managerId, String identifier, String configuration = TestData.roomConfiguration) {
        String id = UUIDGenerator.randomUUID()
        String query = "INSERT INTO rooms (id, manager_id, identifier, \"configuration\", journal, deleted_at) " +
                "VALUES ('$id', '$managerId', '$identifier', '$configuration', '${getCreatedJournalEntry()}', NULL)"
        executeQuery(query, db.&execute)
        return id
    }

    List<GroovyRowResult> getRoom(String id) {
        return getRows("rooms", id)
    }

    void terminateRoom(String roomId) {
        softDelete("rooms", roomId)
    }

    String addEventType(String roomId, String identifier) {
        String id = UUIDGenerator.randomUUID()
        String query = "INSERT INTO event_types(id, room_id, identifier, journal, deleted_at) " +
                "VALUES ('$id', '$roomId', '$identifier', '${getCreatedJournalEntry()}', NULL)"
        executeQuery(query, db.&execute)
        return id
    }

    List<GroovyRowResult> getEventTypes(String id) {
        return getRows("event_types", id)
    }

    void terminateEventType(String eventTypeId) {
        softDelete("event_types", eventTypeId)
    }

    String addEventTypeVersion(String eventTypeId, String jsonSchema = TestData.jsonSchema, int version = 1) {
        String id = UUIDGenerator.randomUUID()
        String query = "INSERT INTO event_type_versions (id, event_type_id, \"version\", json_schema, journal, deleted_at) " +
                "VALUES('$id', '$eventTypeId', $version, '$jsonSchema', '${getCreatedJournalEntry()}', NULL)"
        executeQuery(query, db.&execute)
        return id
    }

    List<GroovyRowResult> getEventTypeVersions(String id) {
        return getRows("event_type_versions", id)
    }

    String addPublisher(String roomId, String publisherId, IdentifierType identifierType = IdentifierType.INTERNAL) {
        String id = UUIDGenerator.randomUUID()
        String query = "INSERT INTO publishers (id, room_id, identifier_type, identifier, journal, deleted_at) " +
                "VALUES ('$id', '$roomId', '${identifierType.name()}', '$publisherId', '${getCreatedJournalEntry()}', NULL)"
        executeQuery(query, db.&execute)
        return id
    }

    List<GroovyRowResult> getPublishers(String id) {
        return getRows("publishers", id)
    }

    void terminatePublisher(String publisherId) {
        softDelete("publishers", publisherId)
    }

    String addPublisherConstraint(String publisherId, String eventTypeId) {
        String id = UUIDGenerator.randomUUID()
        String query = "INSERT INTO publisher_constraints (id, publisher_id, event_type_id, created_at, deleted_at) " +
                "VALUES ('$id', '$publisherId', '$eventTypeId', now(), NULL)"
        executeQuery(query, db.&execute)
        return id
    }

    List<GroovyRowResult> getPublisherConstraints(String id) {
        return getRows("publisher_constraints", id)
    }

    String addSubscription(String roomId, String eventTypeId, String identifier,
                           Map parameters = TestData.pullSubscriptionParameter,
                           IdentifierType identifierType = IdentifierType.XROAD) {
        String id = UUIDGenerator.randomUUID()
        String query = "INSERT INTO subscriptions (id, room_id, event_type_id, identifier_type, identifier, parameters, " +
                "journal, deleted_at) " + "VALUES ('$id', '$roomId', '$eventTypeId', '${identifierType.name()}', " +
                "'$identifier', '${Utils.mapToJsonString(parameters)}', '${getCreatedJournalEntry()}', NULL)"
        executeQuery(query, db.&execute)
        addSubscriptionStatus(id, Subscription.Status.PENDING)
        return id
    }

    String addSubscriptionStatus(String subscriptionId, Subscription.Status status) {
        String id = UUIDGenerator.randomUUID()
        String query = "INSERT INTO subscription_status (id, subscription_id, status, updated_at) " +
                "VALUES ('$id', '$subscriptionId', '${status.name()}', now())"
        executeQuery(query, db.&execute)
        return id
    }

    String changeSubscriptionStatus(String subscriptionId, Subscription.Status newStatus) {
        String id = addSubscriptionStatus(subscriptionId, newStatus)
        boolean softDelete = [Subscription.Status.REJECTED, Subscription.Status.TERMINATED].contains(newStatus)
        updateJournal("subscriptions", subscriptionId, softDelete)
        return id
    }

    List<GroovyRowResult> getSubscriptionStatuses(String subscriptionId) {
        String query = "SELECT *\n" +
                "FROM subscription_status ss\n" +
                "WHERE subscription_id = '$subscriptionId'\n" +
                "ORDER BY updated_at DESC"
        return getRows(query)
    }

    void terminateSubscription(String subscriptionId) {
        softDelete("subscriptions", subscriptionId)
    }

    List<GroovyRowResult> getSubscription(String id) {
        return getRows("subscriptions", id)
    }

    void updateJournal(String table, String id, boolean softDelete = false) {
        String query = "UPDATE $table\n" +
                "SET journal = journal || '${getModifiedJournalEntry()}'::JSONB" +
                "${softDelete ? ",\n    deleted_at = now()\n" : "\n"}" +
                "WHERE id = '$id'"
        executeQuery(query, db.&execute)
    }

    List<GroovyRowResult> getEvents(String roomId, String eventTypeVersionId, String eventTypeId, String publisherId) {
        String query = "SELECT *\n" +
                "FROM EVENTS\n" +
                "WHERE room_id = '$roomId'\n" +
                "  AND event_type_version_id = '$eventTypeVersionId'\n" +
                "  AND event_type_id = '$eventTypeId'\n" +
                "  AND publisher_id = '$publisherId'"
        return getRows(query)
    }

    List<GroovyRowResult> getPublishedMessages(String eventId, String subscriptionId = null) {
        String query = "SELECT *\n" +
                "FROM published_messages\n" +
                "WHERE event_id = '$eventId'\n" +
                "  AND published_at IS NOT NULL"
        if (subscriptionId != null) {
            query += "  AND subscription_id = '$subscriptionId'\n"
        }
        return getRows(query)
    }

    List<GroovyRowResult> getDeliveredPublishedMessages(String subscriptionId, String eventId = null) {
        String query = "SELECT *\n" +
                "FROM published_messages\n" +
                "WHERE subscription_id = '$subscriptionId'\n" +
                "  AND delivered_at IS NOT NULL"
        if (eventId != null) {
            query += "  AND event_id = '$eventId'\n"
        }
        return getRows(query)
    }

    List<GroovyRowResult> getRows(String tableName, String id) {
        String query = "SELECT *\n" +
                "FROM $tableName \n" +
                "WHERE id = '$id'"
        return getRows(query)
    }

    void softDelete(String table, String id) {
        String query = "UPDATE $table\n" +
                "SET deleted_at = now()\n" +
                "WHERE id = '$id'"
        executeQuery(query, db.&execute)
    }

    void truncateAllTables() {
        String getTablesQuery = "SELECT string_agg(TABLE_NAME, ', ') AS tables_list\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_schema = 'im_msg'\n" +
                "  AND TABLE_NAME not in ('databasechangeloglock',\n" +
                "                         'databasechangelog')"
        String tablesList = db.firstRow(getTablesQuery).tables_list
        String truncateQuery = "Truncate $tablesList"
        executeQuery(truncateQuery, db.&execute)
    }

    @Step("DB query")
    static def executeQuery(String query, Closure executionMethod) {
        println "Executing SQL query:\n $query\n" // TODO: replace with logging
        return executionMethod.call(query)
    }

    @Step("DB results")
    List<GroovyRowResult> getRows(String query) {
        List<GroovyRowResult> rows = (List<GroovyRowResult>) executeQuery(query, db.&rows)
        println "Got rows(${rows.size()}):"
        rows.forEach { row -> println(row) }
        println "\n"
        return rows
    }

    static String getCreatedJournalEntry() {
        return """[{"at": "${Instant.now()}", "by": "TESTS", "action": "CREATED"}]"""
    }

    static String getModifiedJournalEntry() {
        return """[{"at": "${Instant.now()}", "by": "TESTS", "action": "MODIFIED"}]"""
    }

}
