# Generation of JOOQ classes from base tables

```shell
gradle generateJooq
```

If necessary, you can change the parameters of the base connection with gradle properties.

```properties
# gradle.properties
jooqDbUrl=jdbc:postgresql://localhost:5432/im_msg_db
jooqDbSchema=im_msg
jooqDbUser=im_msg_usr
jooqDbPassword=im_msg_pass
```
