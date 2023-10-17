# X-Road Security Server Admin API Client Adapter

Adapter for consuming X-Road Security Server Admin API. Requires X-Road Security Server admin
API key for authentication and authorization. See
https://docs.x-road.global/Manuals/ug-ss_x-road_6_security_server_user_guide.html#23-managing-api-keys
for more information about API key management.

API is usually published at `https://<security-server-host>:4000/api/v1/`.

### Openapi file generation

Project includes X-Road Security Server (version 7.2.2) Admin API OpenApi specification file in
`openapi-definition.yaml`. This file is used to generate client code for the API.

Use openapi-generator-cli (https://github.com/OpenAPITools/openapi-generator-cli)

```shell
(cd api-generator
openapi-generator-cli generate -c openapitools.json
cp -r src/main/java/com/govstack/information_mediator/pubsub/management/xroad_admin/client ../src/main/java/com/govstack/information_mediator/pubsub/management/xroad_admin
)
```

### Trusting X-Road Admin API

Depending on X-Road Security Server configuration, it may be necessary to trust the certificate
of the server. In GovStack sandbox environment, all X-Road Security Servers use self-signed
certificates. Use `./generate-truststore.sh` to import the certificate(s) to the trust store.

```shell
./generate-truststore.sh
```

Truststore should be configured in `src/main/resources/management-api-xroad-admin.properties`:

```properties
management.xroad-admin.client.trust-store=classpath:truststore.jks
management.xroad-admin.trust-store-password=changeit
```

For dev purposes only, the TLS certificate verification can be disabled by setting the following parameter to true

```properties
management.xroad-admin.client.disableHostnameVerification
```
