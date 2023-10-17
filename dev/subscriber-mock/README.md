# Subscriber Mock Service

This is a simple express (node.js) application that is used during development to test the process of handing events for PUSH subscriptions.

This application exposes the following endpoints:
- /swagger-ui - API documentation for the subscriber
- /v3/api-docs - API documentation in json-format
- /api/health - a health check endpoint
- /api/callback - a callback endpoint to receive and log events

## Requirements

A nodejs 18.17.1 or recent LTS version.

## Local Development
```bash
  npm install
  npm start
```

## Build and Deploy

1. Build image
  ```bash
    docker build -t subscriber-mock .
  ```

2. Publish image
  ```bash
    docker tag subscriber-mock:latest localhost:5000/subscriber-mock:latest
    docker push localhost:5000/subscriber-mock:latest
  ```

3. Deploy image
  ```bash
    kubectl apply -f ./deployment.yaml
  ```

4. (OPTIONAL) Destroy image
  ```bash
    kubectl delete -k ./deployment
  ```
