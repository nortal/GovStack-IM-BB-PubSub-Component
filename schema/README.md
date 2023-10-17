# Liquibase

## Launching basic updates

### Gradle

```shell
gradle update
```

Base connection parameters
```properties
# gradle.properties
liquibaseJdbcUrl=jdbc:postgresql://localhost:5432/im_msg_db?currentSchema=im_msg
liquibaseUser=im_msg_usr
liquibasePassword=im_msg_pass
```

### Liquibase CLI (3.x)
```shell
liquibaseDbUrl=jdbc:postgresql://localhost:5432/im_msg_db?currentSchema=im_msg
liquibaseUser=im_msg_usr
liquibasePassword=im_msg_pass

liquibase \
    --logLevel=info \
    --classpath=./changelog \
    --changeLogFile=db.changelog-master.yaml \
    --url="$liquibaseJdbcUrl" \
    --username="$liquibaseUser" \
    --password="$liquibasePassword" \
    update
```

## Clear database

### Gradle

```shell
gradle dropAll
```

### Liquibase CLI

```shell
liquibase \
    --url="$liquibaseJdbcUrl" \
    --username="$liquibaseUser" \
    --password="$liquibasePassword" \
    dropAll
```

