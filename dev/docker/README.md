# Running the whole stack in docker

1. Build the management-api:
    ```bash
    (cd ../..
    ./gradlew management-api:bootBuildImage --imageName=management-api:latest
    )
    ```
2. Build the management-ui:
    ```bash
    (cd ../../management-ui/
    docker build --load -f deployment/Dockerfile -t management-ui:latest --target production-stage .
    )
    ```
3. Build the messaging-api:
    ```bash
    (cd ../..
    ./gradlew messaging-api:assemble \
    :messaging-api:build \
    -Dquarkus.container-image.build=true \
    -Dquarkus.container-image.name=messaging-api \
    -Dquarkus.container-image.group="" \
    -Dquarkus.container-image.tag=latest
    )
    ```
4. Build schema
    ```bash
    (cd ../..
    ./gradlew schema:buildImage -PimageName=schema:latest
    )
    ```
5. Run the whole stack
    ```bash
    docker compose up -d
    ```
6. URL-s:
   * [management-api](http://localhost:8080)
   * [messaging-api](http://localhost:8090)
   * [management-ui](http://localhost:5173)
