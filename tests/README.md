# Running tests
*NB!* Environment must be running locally, see [how to start local environment](../dev/docker/README.md) or:
## Running local env
* Build
  ```shell
  (cd ..
  ./gradlew clean
  ./gradlew management-api:bootBuildImage --imageName=management-api:latest
  ./gradlew messaging-api:assemble \
    :messaging-api:build \
    -Dquarkus.container-image.build=true \
    -Dquarkus.container-image.name=messaging-api \
    -Dquarkus.container-image.group="" \
    -Dquarkus.container-image.tag=latest
  ./gradlew schema:buildImage -PimageName=schema:latest
  cd management-ui
  docker build --load -f deployment/Dockerfile -t management-ui:latest --target production-stage .)
  ```
* Startup
  ```shell
  (cd ..
  docker compose -f dev/docker/docker-compose.yaml up -d)
  ```
* Stop env
  ```shell
  (cd ..
  docker compose -p local down -v --remove-orphans)
  ```

## Locally in IDE
* Set up `application-local.properties` based on [sample](src/test/resources/application-local.properties.sample)

Just run the test class or method

## Locally from the command line
* Set up `application-local.properties` based on [sample](src/test/resources/application-local.properties.sample)
  ```shell
  ../gradlew clean test
  ```

## In Docker
This is how tests are run in CI
  ```shell
  docker compose up --exit-code-from tests
  ```

# Running a subset of tests
## When running locally from the command line
Pass in `-DtestFilterExclude` or `-DtestFilterInclude`, with the value(s) of `@Tag` or `@UseCase` annotation e.g:
  ```shell
  ../gradlew clean test -DtestFilterExclude="XRoad"
  ../gradlew clean test -DtestFilterExclude="XRoad, Wiremock"
  ../gradlew clean test -DtestFilterInclude="API"
  ../gradlew clean test -DtestFilterInclude="API"
  ../gradlew clean test -DtestFilterInclude="UC6.1"
  ```
You can also just pass in the name of the class of the tests to be run:
  ```shell
  ../gradlew clean test --tests "PublishTests"
  ```

## When running tests in Docker
Use environment variables TEST_FILTER_INCLUDE and/or TEST_FILTER_EXCLUDE. E.g
  ```shell
  TEST_FILTER_EXCLUDE=-DtestFilterExclude="XRoad, Wiremock" docker compose up --exit-code-from tests
  ```

# Running tests against another environment
In the current test setup there are dependencies:
* Tests with tag `@Tag("UI")` depend on an external KeyCloak instance.
  * The url is set in the [application.propterties](src/test/resources/application.properties) and it needs to have this [KeyCloak conf](../dev/keycloak/config/pubsub-realm-local.json)
  * Keycloak url-s are referenced also in [PubSub local instance docker compose](../dev/docker/docker-compose.yaml)
* Tests with tag `@Tag("XRoad")` depend on external XRoad instances. The sandbox deploy pipeline will set up the needed instances.
* Other tests only need [PubSub local instance](../dev/docker/docker-compose.yaml).

# Report
**NB! [Allure needs to be available from the commandline](https://docs.qameta.io/allure/#_installing_a_commandline)**
## Generating Allure report
  ```shell
  allure serve build/allure-results
  ```

## View sample allure report
**NB! Data from a real test run**
  ```shell
  allure open sample-allure-report
  ```
