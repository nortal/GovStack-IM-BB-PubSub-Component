FROM liquibase/liquibase:4.21-alpine

ENV JDBC_URL="jdbc:postgresql://localhost:5432/im_msg_db?currentSchema=im_msg"
ENV DB_USER="im_msg_usr"
ENV DB_PASSWORD="im_msg_pass"
ENV LOG_LEVEL="info"
ENV COMMAND="update"

ADD changelog /liquibase/changelog

ENTRYPOINT liquibase \
    --changeLogFile=changelog/db.changelog-master.yaml \
    --url=${JDBC_URL} \
    --username=${DB_USER} \
    --password=${DB_PASSWORD} \
    --log-level=${LOG_LEVEL} \
    ${COMMAND}
