# Performance Tests

[Gatling](https://gatling.io/) tests for messaging-api.

## 1. Running Performance tests

1. Import test data from `data.sql`. Example command form importing data to docker based development environment can be
   found in [Test Data](#3-test-data) section
2. Adjust parameters in [configuration.properties](./src/gatling/resources/configuration.properties)
3. Run `gatlingRun` gradle task
    ```sh
    ../gradlew gatlingRun
    ```
   To run only publish-push test, run:
   ```sh
   ../gradlew gatlingRun-com.govstack.information_mediator.perftest.PublishPushSimulation
   ```
   To run only publish-pull test, run:
   ```sh
   ../gradlew gatlingRun-com.govstack.information_mediator.perftest.PublishPullSimulation
   ```

## 2. Test Methods

Tests cover the following scenarios:
- Message is published to message room with `PUSH` subscribers.
- Message is published to message room with `PULL` subscribers.

Both scenarios expect [test data](#3-test-data) to be present in database.

### 2.1 Publish-Push Scenario

The following actions are executed:
1. Publish message to message room using `/rooms/{memberId}/{roomId}/publish` endpoint
2. Repeatedly query database, until message is marked delivered for all subscribers

> **Note:** repeated database query counts as failed in test report unless all messages are delivered.

### 2.2 Publish-Pull Scenario

The following actions are executed:
1. Publish message to message room using `/rooms/{memberId}/{roomId}/publish` endpoint
2. Repeatedly query `/rooms/{memberId}/{roomId}/pull` endpoint until message is returned

## 3. Test data

Test data was initially created with [functional tests](../tests).

`data.sql` was exported with command:
```bash
docker exec -u postgres local-db-1 pg_dump \
  --data-only --inserts --schema im_msg \
  --exclude-table-data='*.databasechangelog*' \
  --exclude-table-data='*.events' \
  --exclude-table-data='*.published_messages' \
  -U im_msg_usr im_msg_db > data.sql
```

Test data can be imported to empty database with following command:
```bash
docker exec -i -u postgres local-db-1 psql -U im_msg_usr -d im_msg_db < data.sql
```
