## Add prometheus helm chart
```bash
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
```

## Install prometheus helm chart
```bash
helm install prometheus prometheus-community/prometheus
```
OR if you want to install with custom values, make a copy of values-default.yaml as values.yaml and edit it, then run:
```bash
helm install prometheus prometheus-community/prometheus -f values.yaml
```
OR when running from root of this repo:
```bash
helm install prometheus prometheus-community/prometheus -f dev/prometheus/values-default.yaml
```

To access prometheus dashboard, run:
```bash
kubectl --namespace default port-forward svc/prometheus-server 9090
kubectl port-forward <prometheus-pod-name> 9090
```
Then open http://localhost:9090 in your browser.
