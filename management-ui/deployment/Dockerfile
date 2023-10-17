FROM node:18.16.0-alpine as setup-stage
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .

FROM setup-stage as test-stage
RUN npm run test:unit-with-reports

FROM sonarsource/sonar-scanner-cli as sonarqube-stage
COPY --from=test-stage /app/coverage/lcov.info lcov.info
COPY --from=test-stage /app/build/sonar-report.xml sonar-report.xml
COPY --from=test-stage /app/sonar-project.properties sonar-project.properties
COPY --from=test-stage /app/src src

ARG BRANCH_NAME
ARG SONAR_HOST_URL
ARG SONAR_LOGIN
RUN sonar-scanner  \
    -Dsonar.qualitygate.wait=false  \
    -Dsonar.host.url=$SONAR_HOST_URL  \
    -Dsonar.login=$SONAR_LOGIN  \
    -Dsonar.branch.name=$BRANCH_NAME  \
    -Dsonar.javascript.lcov.reportPaths="lcov.info"  \
    -Dsonar.testExecutionReportPaths="sonar-report.xml"

FROM setup-stage as build-stage
RUN npm run build

FROM nginx:1.24-alpine as production-stage

ENV VUE_APP_OIDC_PROVIDER_URL="http://host.docker.internal:8089/realms/pubsub-realm"
ENV VUE_APP_OIDC_CLIENT_SECRET="oidc_pass_from_docker"
ENV VUE_APP_MANAGEMENT_API_URI="http://localhost:8080"

COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY ./deployment/nginx.conf /etc/nginx/nginx.conf
COPY ./deployment/entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh

EXPOSE 8080
ENTRYPOINT ["/entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]
