## OAuth2 mock with Kubernetes

### Prerequisites

- A local Kubernetes cluster running (im-bb-dev-cluster, set up in shown in ../README.md)

### Package and install the helm chart (in one step)
```sh
helm install wiremock-chart .
```

#### If needed, uninstall the helm chart with
```sh
helm uninstall wiremock-chart
```

### Waiting until Wiremock is ready
Monitor the Wiremock pod until it's running (should show "Started container wiremock")
(first load takes a while wiremock image is pulled):
```sh
kubectl describe pod -l app=wiremock
```

A sample request sequence:
```http
GET http://pubsub.test:8088/realms/pubsub-realm/protocol/openid-connect/auth?response_type=code&client_id=pubsub-component
POST http://pubsub.test:8088/realms/pubsub-realm/protocol/openid-connect/token
GET http://pubsub.test:8088/realms/pubsub-realm/protocol/openid-connect/userinfo
```

Also available:
```
GET http://pubsub.test:8088/realms/pubsub-realm/.well-known/openid-configuration
```
