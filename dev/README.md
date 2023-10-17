# Dev environment setup

## Requirements

* [Rancher Desktop](https://rancherdesktop.io/) dockerd(moby) container engine for MacOS/Windows
* [Homebrew](https//brew.sh/) (recommended) package manager for MacOS/Windows.
  ```bash
  /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
  ```
* [k3d](https://k3d.io/) lightweight wrapper to run k3s (Rancher Lab’s minimal Kubernetes distribution) in docker.
  ```bash
  brew install k3d
  ```
* [Helm](https://helm.sh/) package manager for Kubernetes
  ```bash
  brew install helm
  ```
  Register helm repositories
  ```bash
  helm repo add rancher-latest https://releases.rancher.com/server-charts/latest
  helm repo add bitnami https://charts.bitnami.com/bitnami
  helm repo update
  ```

## Build and deploy DEV environment

In order to run locally built docker images on k3d cluster, we need to create a local pull-through docker registry.
If image is not found from the local registry the external registry is queried. Create

1. Create docker registry for local development cluster
   ```bash
   (cd ..
   k3d registry create im-bb-registry.localhost --port 5000
   )
   ```
2. Start cluster with using given registry
    ```bash
    (cd..
   k3d cluster create \
   -p "4000:4000@loadbalancer" \
   -p "8080:8080@loadbalancer" \
   -p "8088:8088@loadbalancer" \
   -p "8090:8090@loadbalancer" \
   -p "8089:30089@loadbalancer" \
   -p "5432:5432@loadbalancer" \
   -p "9001:30080@loadbalancer" \
   -p "9002:30081@loadbalancer" \
   -p "8161:8161@loadbalancer" \
   -p "5672:5672@loadbalancer" \
   -p "61616:61616@loadbalancer" \
   --registry-use k3d-im-bb-registry.localhost:5000 \
   --image rancher/k3s:v1.24.12-k3s1 \
   --agents 2 \
   im-bb-dev-cluster
   )
    ```
3. Create namespace for im-bb pubsub components and deploy Rancher UI to ease your cluster management.
   (https://medium.com/47billion/playing-with-kubernetes-using-k3d-and-rancher-78126d341d23)
   ```bash
   (cd ..
   helm install rancher rancher-latest/rancher \
     --namespace cattle-system \
     --create-namespace \
     --set ingress.enabled=false \
     --set tls=external \
     --set replicas=1
   )
   ```
4. Make Rancher UI accessible from outside the cluster. Application will be published at
   https://localhost:9002 (takes a few minutes)
   ```bash
   (cd ..
   kubectl apply -f dev/rancher-ui-deployment.yaml
   )
   ```
5. Build images
   ```bash
   (cd ..
   ./gradlew :schema:buildImage -PimageName=schema
   ./gradlew :management-api:bootBuildImage --imageName=management-api
   ./gradlew :messaging-api:build \
     -Dquarkus.container-image.build=true \
     -Dquarkus.container-image.name=messaging-api \
     -Dquarkus.container-image.group="" \
     -Dquarkus.container-image.tag=latest
   )
   ```
6. Publish images
   ```bash
   (cd ..
   docker tag schema:latest localhost:5000/schema:latest
   docker push localhost:5000/schema:latest
   docker tag management-api:latest localhost:5000/management-api:latest
   docker push localhost:5000/management-api:latest
   docker tag messaging-api:latest localhost:5000/messaging-api:latest
   docker push localhost:5000/messaging-api:latest
   )
   ```
7. Deploy images and database
   ```bash
   (cd ..
   helm install im-msg-db bitnami/postgresql -f dev/im-msg-db/values.yaml --set image.tag=15.2.0 --version=12.2.7 --namespace sandbox-im --create-namespace
   kubectl apply -k schema/deployment
   kubectl apply -f management-api/deployment.yaml
   kubectl apply -f messaging-api/deployment.yaml
   )
   ```
8. (OPTIONAL) Destroy images
   ```bash
   (cd ..
   helm uninstall im-msg-db
   kubectl delete -k schema/deployment
   kubectl delete -f management-api/deployment.yaml
   kubectl delete -f messaging-api/deployment.yaml
   )
   ```
9. (Optional) Delete cluster
   ```bash
   (cd ..
   k3d cluster delete  im-bb-dev-cluster
   )
   ```
